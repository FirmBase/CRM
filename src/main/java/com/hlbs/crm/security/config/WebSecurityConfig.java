package com.hlbs.crm.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

import com.hlbs.crm.enumerations.UserRoleEnum;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
	// Configuring HttpSecurity
	@Bean
	public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) {
		return httpSecurity
			.csrf(csrf -> csrf.disable())
			.csrf(csrf -> csrf.disable())
			.headers(headers -> headers
				// .xssProtection(xss -> xss.disable())	// NEVER do this. Disabling XSS protection should never be in a real application.
				.xssProtection(xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
				.frameOptions(frame -> frame.sameOrigin())
				.contentTypeOptions(content -> content.disable())
				.httpStrictTransportSecurity(hsts -> hsts
					.includeSubDomains(true)
					.maxAgeInSeconds(31536000)	// 1 year in seconds
				)
				// .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'; script-src 'self'; style-src 'self';"))
			)
			.authorizeHttpRequests(requests -> requests
				.requestMatchers("/users/register", "/users/email-available", "/css/**", "/js/**", "/bootstrap/css/*", "/bootstrap/js/*", "/jQuery/js/*", "/datatable/css/*", "/datatable/js/*", "/GoogleFonts/svg/*").permitAll()
			)
			.authorizeHttpRequests(requests -> requests
				.requestMatchers("/users/manage/**")
				.hasRole(UserRoleEnum.ADMIN.name())
			)
			.authorizeHttpRequests(requests -> requests
				.requestMatchers("/crm/**")
				.hasAnyRole(UserRoleEnum.SALESMAN.name(), UserRoleEnum.ACCOUNTANT.name())
			)
			.authorizeHttpRequests(requests -> requests.anyRequest().authenticated())
			.formLogin(form -> {
				form
				.loginPage("/users/login")
				.defaultSuccessUrl("/")
				.failureUrl("/users/login?error=true")
				.permitAll();
			})
			.rememberMe(Customizer.withDefaults())
			.logout(logout -> {
				logout
				.logoutSuccessUrl("/users/login")
				.invalidateHttpSession(true)
				.deleteCookies("CRM")
				.permitAll();
			})
			.build();
	}

	// Password Encoding
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
