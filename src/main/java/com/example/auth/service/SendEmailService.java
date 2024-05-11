package com.example.auth.service;

import com.example.auth.dto.RequestAmartek;

import jakarta.mail.MessagingException;

public interface SendEmailService {
    public void sendVerificationEmail(RequestAmartek requestAmartek, String buildMail, String subject)
            throws MessagingException;

    public void confirmRegister(RequestAmartek requestAmartek, String token) throws MessagingException;

    public void getFormChangePassword(RequestAmartek requestAmartek, String token) throws MessagingException;
    public void forgotPassword( RequestAmartek requestAmartek) throws MessagingException;
}
