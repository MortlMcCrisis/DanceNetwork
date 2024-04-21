package com.mortl.dancenetwork.configuration;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final KeycloakJwtTokenConverter keycloakJwtTokenConverter;

  @Bean
  Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .serverUrl("http://localhost:443")
        .realm("dance-network")
        .clientId("dance-network-admin")
        .grantType(OAuth2Constants.PASSWORD)
        .username("admin@dance-network.com")
        .password("admin")
        .build();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(
            requestMatcherRegistry -> requestMatcherRegistry
                //.requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/api/open/v1/**").permitAll()
                .requestMatchers("/api/closed/v1/**").authenticated()
                .anyRequest()
                .denyAll())
                .csrf(csrf -> csrf.disable());

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