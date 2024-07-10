package com.spring.project.service.auth;

import com.spring.project.constant.EmailTemplateEngine;
import com.spring.project.dto.AuthRequest;
import com.spring.project.dto.AuthResponse;
import com.spring.project.dto.RegistrationDto;
import com.spring.project.entity.user.Token;
import com.spring.project.entity.user.User;
import com.spring.project.exception.NotFoundException;
import com.spring.project.repository.RoleRepository;
import com.spring.project.repository.TokenRepository;
import com.spring.project.repository.UserRepository;
import com.spring.project.utils.EmailUtils;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

import static com.spring.project.constant.Constants.ACCOUNT_ACTIVATION_SUBJECT;
import static com.spring.project.constant.Constants.NUMBERS;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final EmailUtils emailUtils;

    @Value("${APPLICATION.MAILING.ACCOUNT.ACTIVATION_URL}")
    private static String ACCOUNT_ACTIVATION_URL;

    public AuthServiceImpl(UserRepository userRepository, TokenRepository tokenRepository, RoleRepository roleRepository, EmailUtils emailUtils) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.emailUtils = emailUtils;
    }

    @Override
    public void registerUser(RegistrationDto register) throws MessagingException {
        try {
            String uuid = generateUserUUID(register.getFirstname(), register.getLastname());
            var userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new NotFoundException("Role Not Found", NOT_FOUND));
            var user = new User();
            
            user.setUuid(uuid);
            user.setRoles(List.of(userRole));
            user.setFirstname(register.getFirstname());
            user.setLastname(register.getLastname());
            user.setEmail(register.getEmail());
            user.setPassword(register.getPassword());
            user.setDateOfBirth(register.getDateOfBirth());
            userRepository.save(user);
            generateActivationTokenAndSendEmail(user);
        } catch (NotFoundException exception) {
            log.error("An Exception occurred while registering a new user");
            log.error(exception.getMessage());
            throw exception;
        } catch (Exception exception) {
            log.error("An Exception occurred");
            log.error(exception.getMessage());
            throw exception;
        }

    }

    @Override
    public void activateAccount(String token) {

    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        return null;
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
