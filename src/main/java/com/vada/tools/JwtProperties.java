package com.vada.tools;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secretKey;
    private long validityInMs;
    private long expiration;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getValidityInMs() {
        return validityInMs;
    }

    public void setValidityInMs(long validityInMs) {
        this.validityInMs = validityInMs;
    }
    
    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
    
    public String getAuthorizationPrefix() {
        return HttpHeaders.AUTHORIZATION + " ";
    }
}
