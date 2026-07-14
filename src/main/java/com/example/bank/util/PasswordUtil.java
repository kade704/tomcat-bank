package com.example.bank.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtil {

    public static byte[] getSalt() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("알고리즘을 찾을 수 없습니다.", e);
        }
    }

    public static String getSHA256(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt); // 솔트 적용
            byte[] bytes = md.digest(password.getBytes());
            
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("알고리즘을 찾을 수 없습니다.", e);
        }
    }
}