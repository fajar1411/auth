package com.example.auth.service;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.RequestAmartek;

import jakarta.mail.MessagingException;

public interface FajarService {
    public ResponseEntity<Object> createdData(RequestAmartek requestAmartek, String siteUrl)throws MessagingException;

    public ResponseEntity<Object> FindAll();

    public ResponseEntity<Object> codeKata(String kata);

    public ResponseEntity<Object> getFormChangePassword() throws MessagingException;

    public ResponseEntity<Object> loginUser(LoginRequest loginRequest);

    public ResponseEntity<Object> forgotPassword(RequestAmartek requestAmartek) throws MessagingException;

    public String refreshToken(String token) ;

    // https://swapi.dev/api/people
}
