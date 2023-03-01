package com.ihc.apirest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ihc.apirest.usecase.ProcessToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;



@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter 
{

  @Autowired
  ProcessToken processToken;


  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException 
  {
    String token = processToken.getToken(req.getHeader("Authorization"));

    if (null != token)
    {
      UsernamePasswordAuthenticationToken usernamePAT = processToken.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(usernamePAT);
    }
    
    filterChain.doFilter(req, res);
  }
}
