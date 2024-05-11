package com.example.auth.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.RequestAmartek;
import com.example.auth.service.ConfirmRegister;
import com.example.auth.service.FajarService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("api")
public class FajarRestController {
    @Autowired
    private FajarService fajarService;

    @Autowired
    private ConfirmRegister confirmRegister;

    @PostMapping("auth/created")
    public ResponseEntity<Object> createdData(@RequestBody RequestAmartek requestAmartek, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        return fajarService.createdData(requestAmartek, getSiteURL(request));
    }

    @GetMapping("view")
    public ResponseEntity<Object> ViewData(@RequestBody RequestAmartek requestAmartek) {
        return fajarService.FindAll();
    }

    @GetMapping("code")
    public ResponseEntity<Object> cekCode(@RequestParam("kata") String kata) {
        return fajarService.codeKata(kata);
    }

    @PostMapping("code")
    public ResponseEntity<Object> ceKalimat(@RequestBody String kata) {
        return fajarService.codeKata(kata);
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @PostMapping("login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest loginRequest) {
        return fajarService.loginUser(loginRequest);
    }

    @GetMapping("auth/confirm")
    public ResponseEntity<Object> confirmToken(@RequestParam("token") String token) {
        return confirmRegister.confirmToken(token);
    }

    @GetMapping("auth/form-change-password")
    public ResponseEntity<Object> getFormChangePassword() throws MessagingException, UnsupportedEncodingException {
        return fajarService.getFormChangePassword();
    }

    @PostMapping("auth/forgot-password")
    public ResponseEntity<Object> forgotPassword(@RequestBody RequestAmartek requestAmartek)
            throws MessagingException, UnsupportedEncodingException {
        return fajarService.forgotPassword(requestAmartek);
    }
}