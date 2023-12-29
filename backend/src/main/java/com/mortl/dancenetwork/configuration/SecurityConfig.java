package com.mortl.dancenetwork.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final KeycloakJwtTokenConverter keycloakJwtTokenConverter;
  //private final KeycloakAuthenticationEntryPoint authenticationEntryPoint;

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
        //.authenticationEntryPoint(authenticationEntryPoint));

    /*http.oauth2Login(oauth -> oauth
        .authorizationEndpoint(conf -> conf
            .authorizationRedirectStrategy(new DefaultRedirectStrategy())
        )
    );*/

    // Return 401 (unauthorized) instead of 302 (redirect to login) when
    // authorization is missing or invalid
    //http.exceptionHandling(eh -> eh.authenticationEntryPoint((request, response, authException) -> {
    //  response.sendRedirect("http://localhost:443/realms/dance-network/protocol/openid-connect/auth");
    //}));

    http
        .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
  }
}