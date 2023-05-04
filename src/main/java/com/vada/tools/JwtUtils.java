package com.vada.tools;

import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.vada.interfaces.impl.UserDetailsImpl;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils implements Serializable {
	@Serial
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

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUserNameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public ResponseCookie generateJwtCookie(String token) {
		return ResponseCookie.from("access_token", token).path("/api").maxAge(jwtValidity * 100L)
				.httpOnly(true).build();
	}

	public ResponseCookie generateRefreshJwtCookie(String token) {
		return ResponseCookie.from("refresh_token", token).path("/api").maxAge(jwtValidity * 400L)
				.httpOnly(true).build();
	}

	public ResponseCookie getCleanJwtCookie() {
		return ResponseCookie.from(jwtCookie, null).path("/api").build();
	}

	public String generateToken(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		return Jwts.builder()
				.setSubject(authentication.getName())
				.claim(authoritiesKey, authorities)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtValidity * 1000L))
				.signWith(this.key, SignatureAlgorithm.HS512)
				.compact();
	}

	UsernamePasswordAuthenticationToken getAuthenticationToken(final String token, final Authentication existingAuth, final UserDetails userDetails) {
		final Claims claims = Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody();

		final Collection<? extends GrantedAuthority> authorities =
				Arrays.stream(claims.get(authoritiesKey).toString().split(","))
						.map(SimpleGrantedAuthority::new).toList();

		return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
	}
	
}
