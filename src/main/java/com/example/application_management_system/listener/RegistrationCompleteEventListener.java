package com.example.application_management_system.listener;

import com.example.application_management_system.Entity.User;
import com.example.application_management_system.Entity.VerificationToken;
import com.example.application_management_system.event.RegistrationCompleteEvent;
import com.example.application_management_system.repositories.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener
        implements ApplicationListener<RegistrationCompleteEvent> {

    private final VerificationTokenRepository tokenRepository;
    private final JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user = event.getUser();

        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryTime(LocalDateTime.now().plusMinutes(10));
        tokenRepository.save(verificationToken);

        // Build and send the email
        String verifyUrl = "http://localhost:8080/api/auth/verify-email?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Verify your email");
        message.setText("Hi " + user.getFirstName() + ",\n\n"
                + "Click the link below to activate your account:\n"
                + verifyUrl + "\n\n"
                + "This link expires in 10 minutes.");

        mailSender.send(message);
    }
}