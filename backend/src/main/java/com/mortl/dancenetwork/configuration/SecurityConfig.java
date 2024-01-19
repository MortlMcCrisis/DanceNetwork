package com.mortl.dancenetwork.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final KeycloakJwtTokenConverter keycloakJwtTokenConverter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(
            requestMatcherRegistry -> requestMatcherRegistry
                .anyRequest()
                .authenticated());

    http
        .oauth2ResourceServer(resourceServerConfigurer -> resourceServerConfigurer
            .jwt(jwtConfigurer -> jwtConfigurer
                .jwtAuthenticationConverter(keycloakJwtTokenConverter)));

    http
        .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
  }
}