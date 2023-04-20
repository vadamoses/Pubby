package com.vada.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.vada.tools.JWTAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
				.authorizeHttpRequests(authz -> authz.requestMatchers("/swagger-ui.html").permitAll()
						.requestMatchers("/v2/api-docs").permitAll().requestMatchers("/webjars/**").permitAll()
						.requestMatchers("/swagger-resources/**").permitAll().requestMatchers("/swagger-ui/**")
						.permitAll().requestMatchers("/h2-console/**").permitAll().requestMatchers("/users/**")
						.permitAll().requestMatchers("/resources/**").permitAll().requestMatchers("/quiz/**")
						.authenticated().anyRequest().authenticated()
						)
				.formLogin().loginPage("/login").defaultSuccessUrl("/quiz", true).permitAll()
				.and().logout()
				.logoutUrl("/logout") // specify the URL that triggers the logout process
				.logoutSuccessUrl("/login") // specify the URL to redirect to after logout
				.invalidateHttpSession(true) // invalidate the HTTP session on logout
				.deleteCookies("JSESSIONID") // delete any cookies set by the application
				.permitAll()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilterBefore(new JWTAuthenticationFilter(),
						UsernamePasswordAuthenticationFilter.class)
				.httpBasic().disable().headers().frameOptions().disable();

		return http.build();
	}
	

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						// .allowedOriginPatterns("*")
						.allowedOrigins("http://localhost:4200")
						.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS").allowedHeaders("*")
						.exposedHeaders("*").allowCredentials(false).maxAge(3600);
			}
		};
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
		config.addAllowedMethod(HttpMethod.PUT);
		config.addAllowedMethod(HttpMethod.DELETE);
		config.addAllowedMethod(HttpMethod.OPTIONS);
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

}
