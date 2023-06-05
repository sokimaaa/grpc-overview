package com.sokima.grpc.service;

import com.sokima.grpc.model.Credentials;
import com.sokima.grpc.model.EmailCredentials;
import com.sokima.grpc.model.LoginCredentials;
import com.sokima.grpc.model.PhoneCredentials;

public class AuthorizationService {

    public void authorize(Credentials request) {
        switch (request.getModeCase()) {
            case EMAIL_MODE -> {
                EmailCredentials emailMode = request.getEmailMode();
                authorizeViaEmail(emailMode);
            }
            case LOGIN_MODE -> {
                LoginCredentials loginMode = request.getLoginMode();
                authorizeViaLogin(loginMode);
            }
            case PHONE_MODE -> {
                PhoneCredentials phoneMode = request.getPhoneMode();
                authorizeViaPhone(phoneMode);
            }
            case MODE_NOT_SET -> credentialsNotSent();
            default -> throw new CredentialsException();
        }
    }

    private void credentialsNotSent() {

    }

    private void authorizeViaPhone(PhoneCredentials phoneMode) {
        // authorization
    }

    private void authorizeViaLogin(LoginCredentials loginMode) {
        // authorization
    }

    private void authorizeViaEmail(EmailCredentials emailMode) {
        // authorization
    }

    private static class CredentialsException extends RuntimeException {

    }
}
