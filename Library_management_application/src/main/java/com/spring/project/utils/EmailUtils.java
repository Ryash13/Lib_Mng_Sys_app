package com.spring.project.utils;

import com.spring.project.constant.EmailTemplateEngine;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
public class EmailUtils {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailUtils(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String activationCode, String activationUrl, EmailTemplateEngine emailTemplate,
                          String subject, String username) throws MessagingException {
        String templateName = emailTemplate == null ? "account_activation" : emailTemplate.getName();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MULTIPART_MODE_MIXED, UTF_8.name());
        Map<String, Object> templateProperties = new HashMap<>();
        templateProperties.put("username", username);
        templateProperties.put("activation_code", activationCode);
        templateProperties.put("activation_url", activationUrl);

        Context context = new Context();
        context.setVariables(templateProperties);

        helper.setTo(to);
        helper.setFrom("yash.11717121@gmail.com");
        helper.setSubject(subject);

        String template = templateEngine.process(templateName, context);

        helper.setText(template, true);

        mailSender.send(message);
    }
}
