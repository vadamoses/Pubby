package com.vada.tools;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.vada.interfaces.impl.UserDetailsImpl;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${jwt.signing.key}")
	private String signingKey;

	@Value("${jwt.authorities.key}")
	public String authoritiesKey;

	@Value("${jwt.token.validity}")
	private int jwtValidity;

	@Value("${jwt.cookieName}")
	private String jwtCookie;

	@Value("${jwt.cookieRefreshName}")
	private String jwtRefreshCookie;

	private String currentToken;

	private Key key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
	}

	public String getUserNameFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody().getSubject();
	}

	public Date getExpirationDateFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody().getExpiration();
	}

	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
    public boolean validateToken(String token) {
        try {
            	Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token);         	
            	return true;
        } catch (JwtException e) {
            LOGGER.error("Invalid JWT: {}", e.getMessage());
        }
        return false;
    }

	public String getJwtFromCookies(HttpServletRequest request) {
		Cookie cookie = WebUtils.getCookie(request, jwtCookie);
		return (cookie != null) ? cookie.getValue() : null;
	}

	public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
		this.currentToken = generateTokenFromUsername(userPrincipal.getUsername());
		return ResponseCookie.from("access_token", this.currentToken).path("/api").maxAge(2L * jwtValidity)
				.httpOnly(true).build();
	}

	public ResponseCookie generateRefreshJwtCookie(UserDetailsImpl userPrincipal) {
		this.currentToken = generateTokenFromUsername(userPrincipal.getUsername());
		return ResponseCookie.from("refresh_token", this.currentToken).path("/api").maxAge(4L * jwtValidity)
				.httpOnly(true).build();
	}

	public ResponseCookie getCleanJwtCookie() {
		return ResponseCookie.from(jwtCookie, null).path("/api").build();
	}
	
	public String getCurrentToken() {
		return this.currentToken;
	}

	public String generateTokenFromUsername(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtValidity))
				.signWith(this.key, SignatureAlgorithm.HS512).compact();
	}
	
}
