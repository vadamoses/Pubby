package com.vada.tools;

import java.security.Key;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

public class UniqueKeyGenerator {

    public static String generateKey() {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        return Encoders.BASE64.encode(key.getEncoded());
    }

    public static void main(String[] args) {
        String key = generateKey();
        System.out.println("Key value: " + key);
    }
}

