package com.example.auth.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.auth.dto.ChangePassword;
import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.RequestAmartek;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
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
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ConfirmRegister confirmRegister;

    @Autowired
    private UserRepository userRepository;

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

    @PostMapping("auth/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest loginRequest) {
        return fajarService.loginUser(loginRequest);
    }

    @GetMapping("auth/confirm")
    public ResponseEntity<Object> confirmToken(@RequestParam("token") String token) {
        return confirmRegister.confirmToken(token);
    }

    @GetMapping("auth/change-password")
    public ResponseEntity<Object> getFormChangePassword() throws MessagingException, UnsupportedEncodingException {
        return fajarService.getFormChangePassword();
    }

    @PostMapping("auth/forgot-password")
    public ResponseEntity<Object> forgotPassword(@RequestBody RequestAmartek requestAmartek)
            throws MessagingException, UnsupportedEncodingException {
        return fajarService.forgotPassword(requestAmartek);
    }

    @GetMapping("auth/form-change-password/{token}")
    public String formChange(@PathVariable(required = true, name = "token") String token, Model model) {
        model.addAttribute("changePassword", new ChangePassword());
        model.addAttribute("token", token);
        return "auth/form-change-password";
    }

    @PostMapping("/auth/checkpassword/{token}")
    public String check(@PathVariable String token, ChangePassword changePassword, Model model) {
        System.out.println(changePassword.getNewPassword() + " new password");
        System.out.println(changePassword.getOldPassword() + " old password");
        
    
        User user = userRepository.cekToken(token);
        System.out.println(user + " user");
    
        String regexPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$";
    
        if (user == null) {
            model.addAttribute("errorMessage", "user not found");
            model.addAttribute("token", token);
            return "redirect:/api/auth/form-change-password/" + token;
        } else if (!changePassword.getNewPassword().matches(regexPattern)) {
            model.addAttribute("errorMessage", "Password must be at least 8 characters long and contain at least one letter, one number, and one special character.");
            model.addAttribute("token", token);
            return "redirect:/api/auth/form-change-password/" + token;
        } else if (changePassword.getNewPassword().isEmpty()) {
            model.addAttribute("errorMessage", "password not empty");
            model.addAttribute("token", token);
            return "redirect:/api/auth/form-change-password/" + token;
        } else if (changePassword.getNewPassword().equals(changePassword.getOldPassword())) {
            model.addAttribute("errorMessage", "Your new password must not be the same as the old password.");
            model.addAttribute("token", token);
            return "redirect:/api/auth/form-change-password/" + token;
        }else if (!changePassword.getConfirmNew().equals(changePassword.getNewPassword())) {
            model.addAttribute("errorMessage", "Your confrim new password must not be the same as the new password.");
            model.addAttribute("token", token);
            return "redirect:/api/auth/form-change-password/" + token;
        } else {
            user.setPassword(passwordEncoder.encode(changePassword.getConfirmNew()));
            userRepository.save(user);
            model.addAttribute("success", "success");
        }
    
        return "redirect:/api/auth/form-change-password/" + token;
    }
    
    
}