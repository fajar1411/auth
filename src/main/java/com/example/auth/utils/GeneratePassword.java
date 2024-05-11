package com.example.auth.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class GeneratePassword {
    public static String generateRandomPassword() {

        String passwordtype = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";

        String password = RandomStringUtils.random(8, passwordtype);

        return password;
    }
}
