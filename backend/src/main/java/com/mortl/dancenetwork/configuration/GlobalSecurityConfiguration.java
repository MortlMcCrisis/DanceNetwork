//package com.mortl.dancenetwork.configuration;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@RequiredArgsConstructor
//public class GlobalSecurityConfiguration {
//
//  private final KeycloakJwtTokenConverter keycloakJwtTokenConverter;
//  //private final KeycloakAuthenticationEntryPoint authenticationEntryPoint;
//
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http
//        .authorizeHttpRequests(
//            requestMatcherRegistry -> requestMatcherRegistry
//                .anyRequest()
//                .authenticated());
//        //.anyRequest()
//        //.authenticated();
//
//    http
//        .oauth2ResourceServer(resourceServerConfigurer -> resourceServerConfigurer
//            .jwt(jwtConfigurer -> jwtConfigurer
//                .jwtAuthenticationConverter(keycloakJwtTokenConverter)));
//            //.authenticationEntryPoint(authenticationEntryPoint));
//        /*http
//        .oauth2ResourceServer()
//        .jwt()
//        .jwtAuthenticationConverter(keycloakJwtTokenConverter);*/
//
//    http
//        .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//    /*http
//        .sessionManagement()
//        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/
//
//    return http.build();
//
//    /*return http
//        .authorizeRequests(authorizeRequests ->
//            authorizeRequests
//                .anyRequest()
//                .authenticated()
//        )
//        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
//        .sessionManagement(sessionManagement ->
//            sessionManagement
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        ).build();*/
//  }
//}