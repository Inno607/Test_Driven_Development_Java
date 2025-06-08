package com.training.estore.service.Impl;

import com.training.estore.model.User;
import com.training.estore.service.EmailVerificationService;

public class EmailVerificationServiceImpl implements EmailVerificationService {
    @Override
    public void scheduleEmailConfirmation(User user) {
        // Put user details into email queue
        System.out.println("scheduleEmailConfirmation is called");
    }
}
