package com.example.application_management_system.listener;

import com.example.application_management_system.Entity.VerificationToken;
import com.example.application_management_system.event.RegistrationCompleteEvent;
import com.example.application_management_system.repositories.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener
        implements ApplicationListener<RegistrationCompleteEvent> {

    private final VerificationTokenRepository tokenRepository;

    @Override
    public void onApplicationEvent(
            RegistrationCompleteEvent event) {

        User user = event.getUser();

        String token =
                UUID.randomUUID().toString();

        VerificationToken verificationToken =
                new VerificationToken();

        verificationToken.setToken(token);

        verificationToken.setUser(user);

        verificationToken.setExpiryTime(
                LocalDateTime.now().plusMinutes(10));

        tokenRepository.save(verificationToken);

        System.out.println(
                "Verification Token = " + token);
    }
}
