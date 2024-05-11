package com.example.auth.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import com.example.auth.config.MyUserDetails;
import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.RequestAmartek;
import com.example.auth.handler.CustomResponse;
import com.example.auth.model.Amartek;
import com.example.auth.model.TrAmartek;
import com.example.auth.model.User;

import com.example.auth.repository.AmartekRepository;
import com.example.auth.repository.RoleRepository;
import com.example.auth.repository.TrAmartekRepository;
import com.example.auth.repository.UserRepository;
import com.example.auth.service.FajarService;
import com.example.auth.service.SendEmailService;
import com.example.auth.utils.GeneratePassword;
import jakarta.mail.MessagingException;


@Service
public class FajarImplementService implements FajarService {
    @Autowired
    private MyUserDetails myUserDetails;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
   
    @Autowired
    private AmartekRepository amartekRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrAmartekRepository trAmartekRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SendEmailService sendEmailService;

    @Override
    public ResponseEntity<Object> createdData(RequestAmartek requestAmartek, String siteUrl) throws MessagingException {
        if (requestAmartek.getName().equals("") || requestAmartek.getName() == null
                || requestAmartek.getTanggalLahir().isEmpty() || requestAmartek.getTanggalLahir() == null
                || requestAmartek.getEmail().isEmpty() || requestAmartek.getPassword().isEmpty()
                || requestAmartek.getPassword() == null
                || requestAmartek.getEmail() == null) {
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Cek Your Input");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");// untuk memformat atau
                                                                                         // mem-parse string tanggal
        // variable date time tipedatanya adalah sebuah object LocalDateTime yang
        // valuenya berdasarkan konversi string berdasarkan format yang telah ditentukan
        // oleh formatter
        LocalDateTime dateTime = LocalDateTime.parse(requestAmartek.getTanggalLahir(), formatter);
        Amartek amartek = new Amartek();
        amartek.setName(requestAmartek.getName());
        amartek.setTanggalLahir(dateTime);
        amartek.setUmur(requestAmartek.getUmur());
        amartek.setEmail(requestAmartek.getEmail());

        amartekRepository.save(amartek);

        TrAmartek trAmartek = new TrAmartek();
        boolean result = amartekRepository.findById(amartek.getId()).isPresent();
        // is present untuk mengecek apa datanya atau tidak

        if (result) {
            String randomCode = UUID.randomUUID().toString();
            User user = new User();
            com.example.auth.model.Role role = roleRepository.findRole(requestAmartek.getRole());
            user.setId(amartek.getId());
            user.setPassword(passwordEncoder.encode(requestAmartek.getPassword()));
            user.setStatus("nonAktif");
            user.setVerificationCode(randomCode);
            userRepository.save(user);
            requestAmartek.setVerificationCode(randomCode);
            trAmartek.setRole(role);
            trAmartek.setUser(user);
            trAmartekRepository.save(trAmartek);
            Boolean resultUser = userRepository.findById(amartek.getId()).isPresent();
            if (resultUser) {
                sendEmailService.confirmRegister(requestAmartek, randomCode);

                return CustomResponse.generate(HttpStatus.CREATED, "register Successfully Added");
            }

        } else {
            userRepository.deleteById(amartek.getId());
            trAmartekRepository.deleteById(amartek.getId());
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Error Adding Data");
        }

        return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Register Failed");
    }

    @Override
    public ResponseEntity<Object> getFormChangePassword() throws MessagingException {
        User user = userRepository.findUserByEmail("frizky861@gmail.com");

        if (user == null) {
            return CustomResponse.generate(HttpStatus.OK, "account not found");
        }

        RequestAmartek requestAmartek = new RequestAmartek();

        requestAmartek.setName(user.getAmartek().getName());
        requestAmartek.setEmail(user.getAmartek().getEmail());
        String randomCode = UUID.randomUUID().toString();

        // userRepository.save(null);

        sendEmailService.getFormChangePassword(requestAmartek, randomCode);

        return CustomResponse.generate(HttpStatus.OK, "Success view form Change Password");
    }

    @Override
    public ResponseEntity<Object> forgotPassword(RequestAmartek requestAmartek) throws MessagingException {
        User user = userRepository.findUserByEmail(requestAmartek.getEmail());

        String pw = GeneratePassword.generateRandomPassword();
        System.out.println("ini password baru" + pw);

        user.setPassword(passwordEncoder.encode(pw));

        userRepository.save(user);

        requestAmartek.setName(user.getAmartek().getName());
        requestAmartek.setPassword(user.getPassword());

        sendEmailService.forgotPassword(requestAmartek);

        return CustomResponse.generate(HttpStatus.OK, "success send email");
    }

    @Override
    public ResponseEntity<Object> FindAll() {
        return CustomResponse.generate(HttpStatus.OK, "success view data", amartekRepository.findAll());
    }

    @Override
    public ResponseEntity<Object> codeKata(String kata) {
        int counta = 0;
        int counti = 0;
        int countu = 0;
        int counte = 0;
        int counto = 0;
        int countnonvokal = 0;

        Map<String, Integer> response = new HashMap<>();
        for (int i = 0; i < kata.length(); i++) {
            // indonesia
            if (kata.charAt(i) == 'a') {

                counta++;
                response.put("a", counta);

            } else if (kata.charAt(i) == 'i') {
                counti++;
                response.put("i", counti);

            } else if (kata.charAt(i) == 'u') {
                countu++;
                response.put("u", countu);

            } else if (kata.charAt(i) == 'e') {
                counte++;
                response.put("e", counte);

            } else if (kata.charAt(i) == 'o') {
                counto++;
                response.put("o", counto);

            } else {
                countnonvokal++;
                response.put("nonvokal", countnonvokal);
            }

        }
        return CustomResponse.generate(HttpStatus.OK, "success", response);
    }

    @Override
    public ResponseEntity<Object> loginUser(LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = myUserDetails.loadUserByUsername(loginRequest.getEmail());

            return CustomResponse.generate(HttpStatus.OK, "login success",userDetails);
        } catch (Exception e) {
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Login Failed", null);
        }
    }
}
