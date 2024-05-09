package com.example.auth.service;

import org.springframework.http.ResponseEntity;

import com.example.auth.dto.RequestAmartek;

import jakarta.mail.MessagingException;

public interface FajarService {
    public ResponseEntity<Object> createdData(RequestAmartek requestAmartek, String siteUrl)throws MessagingException;

    public ResponseEntity<Object> FindAll();

    public ResponseEntity<Object> codeKata(String kata);
}
