package com.spring.project.service.auth;

import com.spring.project.constant.EmailTemplateEngine;
import com.spring.project.dto.AuthRequest;
import com.spring.project.dto.AuthResponse;
import com.spring.project.dto.RegistrationDto;
import com.spring.project.entity.user.Token;
import com.spring.project.entity.user.User;
import com.spring.project.exception.CustomException;
import com.spring.project.repository.RoleRepository;
import com.spring.project.repository.TokenRepository;
import com.spring.project.repository.UserRepository;
import com.spring.project.security.JwtService;
import com.spring.project.utils.ConverterUtil;
import com.spring.project.utils.EmailUtils;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static com.spring.project.constant.Constants.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final EmailUtils emailUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Value("${APPLICATION.MAILING.ACCOUNT.ACTIVATION_URL}")
    private String ACCOUNT_ACTIVATION_URL;

    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService, TokenRepository tokenRepository, RoleRepository roleRepository, EmailUtils emailUtils, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.emailUtils = emailUtils;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(RegistrationDto register) throws MessagingException {
        try {
            String uuid = generateUserUUID(register.getFirstname(), register.getLastname());
            var userRole = roleRepository.findByName(USER_ROLE)
                    .orElseThrow(() -> new CustomException("Role Not Found", NOT_FOUND));
            var user = new User();
            user = ConverterUtil.convertRegisterDtoToUser(register);
            user.setUuid(uuid);
            user.setId(1);
            user.setRoles(List.of(userRole));
            user.setPassword(passwordEncoder.encode(register.getPassword()));
            userRepository.save(user);
            generateActivationTokenAndSendEmail(user);
        } catch (CustomException exception) {
            log.error("An Exception occurred while registering a new user");
            log.error(exception.getMessage());
            throw exception;
        } catch (Exception exception) {
            exception.printStackTrace();
            log.error(exception.getMessage());
            throw exception;
        }

    }

    @Override
    public void activateAccount(String token) throws MessagingException {
        try {
            var savedToken = tokenRepository.findByToken(token)
                    .orElseThrow(() -> new CustomException("Invalid Token", BAD_REQUEST));
            if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
                generateActivationTokenAndSendEmail(savedToken.getUser());
                throw new CustomException("Token has Expired, Sent a new token to the registered Email ID", BAD_REQUEST);
            }
            var user = savedToken.getUser();
            user.setEnabled(true);
            userRepository.save(user);

            savedToken.setActivated(true);
            savedToken.setActivatedAt(LocalDateTime.now());
            tokenRepository.save(savedToken);
        } catch (CustomException exception) {
            log.error(exception.getMessage());
            throw exception;
        } catch (Exception exception) {
            exception.printStackTrace();
            log.error(exception.getMessage());
            throw exception;
        }
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );
            if(!auth.isAuthenticated()) {
                throw new BadCredentialsException(INVALID_CREDENTIALS);
            }
            var claims = new HashMap<String, Object>();
            var user = (User) auth.getPrincipal();
            claims.put("fullName", user.getName());
            var jwtToken = jwtService.generateToken(claims, user);
            return new AuthResponse(jwtToken, user.getName(), user.getUuid());
        } catch (BadCredentialsException exception) {
            log.error(exception.getMessage());
            throw exception;
        } catch (Exception exception) {
            exception.printStackTrace();
            log.error(exception.getMessage());
            throw exception;
        }
    }

    private String generateUserUUID(String firstName, String lastName) {
        StringBuilder builder = new StringBuilder();
        builder.append(firstName.charAt(0));
        builder.append(lastName.charAt(0));
        String token = generateToken(4);
        builder.append(token);
        return builder.toString();
    }

    private String generateToken(int size) {
        String numbers = NUMBERS;
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int index = random.nextInt(numbers.length());
            builder.append(numbers.charAt(index));
        }
        log.info("Generated Activation Token - {}", builder.toString());
        return builder.toString();
    }

    @Async
    private void generateActivationTokenAndSendEmail(User user) throws MessagingException {
        var generatedToken = generateActivationToken(user);
        String activationUrl = ACCOUNT_ACTIVATION_URL + "?token=" + generatedToken;
        emailUtils.sendEmail(user.getEmail(),
                generatedToken,
                activationUrl,
                EmailTemplateEngine.ACCOUNT_ACTIVATION,
                ACCOUNT_ACTIVATION_SUBJECT,
                user.getName()
        );
    }

    private String generateActivationToken(User user) {
        Token token = new Token();
        String generatedToken = generateToken(6);
        token.setToken(generatedToken);
        token.setActivated(false);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(20));
        token.setUser(user);
        tokenRepository.save(token);
        return generatedToken;
    }
}
