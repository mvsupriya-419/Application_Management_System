package com.example.application_management_system.service;

import com.example.application_management_system.Entity.User;
import com.example.application_management_system.dto.ChangePasswordRequest;
import com.example.application_management_system.dto.RegistrationRequest;
import com.example.application_management_system.dto.ResetPasswordRequest;

public interface UserService {
    User registerUser(RegistrationRequest request);
    String verifyEmail(String token);
    void resendVerificationToken(String email);
    void sendPasswordResetEmail(String email);
    void resetPassword(ResetPasswordRequest request);
    void changePassword(ChangePasswordRequest request);
}