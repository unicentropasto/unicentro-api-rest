package com.ihc.apirest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;


@Configuration
@AllArgsConstructor
public class WebSecurityConfig
{
  private final UserDetailsService userDetailsService;
  private final JWTAuthorizationFilter jwtAuthorizationFilter;



  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception 
  {
    // JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();
    // jwtAuthenticationFilter.setAuthenticationManager(authManager);
    // jwtAuthenticationFilter.setFilterProcessesUrl("/v1/customers/validations/tokens");

    http
        .cors()
        .and()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/customers/signup").permitAll()
        .antMatchers("/customers/signin").permitAll()
        .antMatchers("/customers/restores").permitAll()
        .antMatchers("/categories").permitAll()
        .antMatchers("/menuoptions/{idRole}").permitAll()
        .antMatchers("/promotions/loads").permitAll()
        .antMatchers("/promotions").permitAll()
        .antMatchers("/identificationstype").permitAll()
        .antMatchers("/neighborhoods").permitAll()
        .antMatchers("/stores/loads").permitAll()
        .antMatchers("/aforos/maximos").permitAll()
        .antMatchers("/aforos/ingresos").permitAll()
        .antMatchers("/aforos/salidas").permitAll()
        .antMatchers("/configurations/images/types/{type}").permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        // .addFilter(jwtAuthenticationFilter)
        .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
    ;

    return http.build();
  }


  @Bean
  AuthenticationManager authManager(HttpSecurity http) throws Exception
  {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
  }


  @Bean
	public BCryptPasswordEncoder passwordEncoder() 
	{
    return new BCryptPasswordEncoder();
  }
}