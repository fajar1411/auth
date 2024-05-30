package com.example.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.auth.dto.RequestAmartek;
import com.example.auth.service.SendEmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class SendEmailVerification implements SendEmailService {
  @Autowired
  private JavaMailSender mailSender;
  @Autowired
  private TemplateEngine templateEngine;
  @Value("${application.localhost}")
  private String localhost;

  // Context  menyimpan data yang akan digunakan dalam proses rendering template
//  templateEngine.process untuk memproses template html berdasarkan context yang di buat
  @Override
  public void confirmRegister(RequestAmartek requestAmartek, String token) throws MessagingException {
    String link = String.format("%sapi/auth/confirm?token=%s", localhost, token);
    String subject = "Confirm your account";

    System.out.println(link + " ini link");
  
    Context context = new Context();
    context.setVariable("name", requestAmartek.getName());
    context.setVariable("link", link);

    String buildMail = templateEngine.process("auth/confirm-email.html", context);
    sendVerificationEmail(requestAmartek, buildMail, subject);
  }

  @Override
  public void getFormChangePassword(RequestAmartek requestAmartek, String token) throws MessagingException {
    String link = String.format("%sapi/auth/form-change-password/%s", localhost, token);
    String subject = "Form Change Password";


    Context context = new Context();
    context.setVariable("name", requestAmartek.getName());
    context.setVariable("link", link);

    String buildMail = templateEngine.process("auth/change-password.html", context);
    sendVerificationEmail(requestAmartek, buildMail, subject);
  }

  @Override
  public void forgotPassword(RequestAmartek requestAmartek) throws MessagingException {

    String subject = "New Your Password";


    Context context = new Context();
    context.setVariable("name", requestAmartek.getName());
    context.setVariable("password", requestAmartek.getPassword());

    String buildMail = templateEngine.process("auth/forgot-password.html", context);
    sendVerificationEmail(requestAmartek, buildMail, subject);
  }

  @Override
  public void sendVerificationEmail(RequestAmartek requestAmartek, String buildMail, String subject)
      throws MessagingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
    helper.setText(buildMail, true);
    helper.setTo(requestAmartek.getEmail());
    helper.setSubject(subject);
    helper.setFrom("frizky861@gmail.com");
    mailSender.send(mimeMessage);
  }

}
