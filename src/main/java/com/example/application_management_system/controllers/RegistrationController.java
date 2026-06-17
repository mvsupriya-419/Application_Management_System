package com.example.application_management_system.controllers;

import com.example.application_management_system.Entity.User;
import com.example.application_management_system.dto.ChangePasswordRequest;
import com.example.application_management_system.dto.RegistrationRequest;
import com.example.application_management_system.dto.ResetPasswordRequest;
import com.example.application_management_system.event.RegistrationCompleteEvent;
import com.example.application_management_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    // 1. Register a new user
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        User user = userService.registerUser(request);
        eventPublisher.publishEvent(new RegistrationCompleteEvent(user));
        return ResponseEntity.ok("Registration successful! Check your email to verify your account.");
    }

    // 2. Verify email with token
    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        return ResponseEntity.ok(userService.verifyEmail(token));
    }

    // 3. Resend verification token
    @PostMapping("/resend-token")
    public ResponseEntity<String> resendToken(@RequestParam("email") String email) {
        userService.resendVerificationToken(email);
        return ResponseEntity.ok("A new verification email has been sent.");
    }

    // 4. Forgot password — sends reset link
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email) {
        userService.sendPasswordResetEmail(email);
        return ResponseEntity.ok("Password reset email sent.");
    }

    // 5. Reset password using token
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request);
        return ResponseEntity.ok("Password has been reset successfully.");
    }

    // 6. Change password (logged-in user, requires old password)
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        return ResponseEntity.ok("Password changed successfully.");
    }
}