package com.vada.tools;
import java.security.SecureRandom;
import java.util.Base64;

public class UniqueKeyGenerator {
    private static final int KEY_LENGTH = 64; // 512 bits

    public static String generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[KEY_LENGTH / 8];
        random.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    public static void main(String[] args) {
        String key = generateKey();
        System.out.println(key);
    }
}
