package com.training.estore.service;


import com.training.estore.model.User;

public interface EmailVerificationService {
    void scheduleEmailConfirmation(User user);
}
