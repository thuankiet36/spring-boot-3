package com.kiet.springbootexample.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Profile("!test")
public class SpringSecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     http
       .cors()
       .and()
       .csrf()
       .disable()
       .authorizeHttpRequests()
       .requestMatchers("/api/v1/auth/**")
       .permitAll()
       .requestMatchers("/api/v1/customers/*")
       .permitAll()
       .requestMatchers("/api/v1/roles")
       .permitAll()
       .requestMatchers("/api/v1/roles/*")
       .permitAll()
       .requestMatchers("/api/v1/permissions")
       .permitAll()
       .requestMatchers("/api/v1/role-permission")
       .permitAll()
       .anyRequest()
       .authenticated()
       .and()
       .sessionManagement()
       .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
       .and()
       .authenticationProvider(authenticationProvider)
       .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
     return http.build();
  }
}
