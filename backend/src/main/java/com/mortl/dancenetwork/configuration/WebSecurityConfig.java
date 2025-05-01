package com.mortl.dancenetwork.configuration;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig
{

  private final KeycloakJwtTokenConverter keycloakJwtTokenConverter;

  public WebSecurityConfig(KeycloakJwtTokenConverter keycloakJwtTokenConverter)
  {
    this.keycloakJwtTokenConverter = keycloakJwtTokenConverter;
  }

  @Bean
  Keycloak keycloak()
  {
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
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
  {
    http
        .authorizeHttpRequests(
            requestMatcherRegistry -> requestMatcherRegistry
                //.requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/api/open/v1/**").permitAll()
                .requestMatchers("/images/**").permitAll()
                .requestMatchers("/uploads/**").permitAll()
                .requestMatchers("/api/closed/v1/**").authenticated()
                .anyRequest().denyAll())
        .csrf(AbstractHttpConfigurer::disable);

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