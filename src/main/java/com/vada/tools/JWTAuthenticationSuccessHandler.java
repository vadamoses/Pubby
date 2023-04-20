package com.vada.tools;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.vada.models.AppUser;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtProperties jwtProperties;

	public JWTAuthenticationSuccessHandler(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		AppUser user = (AppUser) authentication.getPrincipal();
		List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
		String token = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
				.withClaim("roles", roles)
				.sign(Algorithm.HMAC256(jwtProperties.getSecretKey().getBytes()));
		response.addHeader(jwtProperties.getAuthorizationHeader(), jwtProperties.getAuthorizationPrefix() + token);
	}
}
