package com.example.application_management_system.service;

import com.example.application_management_system.Entity.User;
import com.example.application_management_system.Entity.VerificationToken;
import com.example.application_management_system.dto.ChangePasswordRequest;
import com.example.application_management_system.dto.RegistrationRequest;
import com.example.application_management_system.dto.ResetPasswordRequest;
import com.example.application_management_system.repositories.UserRepository;
import com.example.application_management_system.repositories.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;

    @Override
    public User registerUser(RegistrationRequest request) {
        // Check if email already exists before trying to save
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered: " + request.getEmail());
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(false);
        return userRepository.save(user);
    }

    // Called by the listener to save the token after registration
    public void saveVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryTime(LocalDateTime.now().plusMinutes(10));
        tokenRepository.save(verificationToken);
    }

    @Override
    public String verifyEmail(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (verificationToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            return "Token has expired. Please request a new one.";
        }

        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        tokenRepository.delete(verificationToken);
        return "Email verified successfully! You can now log in.";
    }

    @Override
    public void resendVerificationToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Delete old token if it exists
        tokenRepository.findByToken(email).ifPresent(tokenRepository::delete);

        String newToken = UUID.randomUUID().toString();
        saveVerificationToken(user, newToken);
        // Send email — inject and call EmailService here
    }

    @Override
    public void sendPasswordResetEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();
        saveVerificationToken(user, token); // reusing same token table
        // Send email with reset link containing the token
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        VerificationToken tokenEntity = tokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (tokenEntity.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired");
        }

        User user = tokenEntity.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        tokenRepository.delete(tokenEntity);
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}