package com.ihc.apirest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;


@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig
{
  private final UserDetailsService userDetailsService;
  private final JWTAuthorizationFilter jwtAuthorizationFilter;



  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception 
  {

    http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable);

    http.authorizeHttpRequests(auth -> auth
                                            .requestMatchers("/customers/signup").permitAll()
                                            .requestMatchers("/customers/signin").permitAll()
                                            .requestMatchers("/customers/restores").permitAll()
                                            .requestMatchers("/categories").permitAll()
                                            .requestMatchers("/menuoptions/{idRole}").permitAll()
                                            .requestMatchers("/promotions").permitAll()
                                            .requestMatchers("/identificationstype").permitAll()
                                            .requestMatchers("/neighborhoods").permitAll()
                                            .requestMatchers("/aforos/maximos").permitAll()
                                            .requestMatchers("/aforos/ingresos").permitAll()
                                            .requestMatchers("/aforos/salidas").permitAll()
                                            .requestMatchers("/configurations/images/types/{type}").permitAll()
                                            .anyRequest()
                                            .authenticated()
                              );

    http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
  }


  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception 
  {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    return authenticationManagerBuilder.build();
  }

  
  @Bean
	public BCryptPasswordEncoder passwordEncoder() 
	{
    return new BCryptPasswordEncoder();
  }
}