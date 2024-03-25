// package com.ihc.apirest.security;

// import java.io.IOException;
// import java.util.Collections;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.ihc.apirest.models.Customer;


// public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter
// {

//   @Override
// 	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException 
//   {
// 		AuthCredentials authCredentials = new AuthCredentials();

//     try
//     {
//       authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
//     }
//     catch(IOException e)
//     {
//       // Error controlado ya que instancio authCredentials en el inicio
//     }

//     UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(authCredentials.getEmail(), authCredentials.getPassword(), Collections.emptyList());

// 		return getAuthenticationManager().authenticate(usernamePAT);
// 	}

  
//   @Override
//   protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException 
//   {
//     Customer customer = (Customer) authResult.getPrincipal();

//     String token = TokenUtils.createToken(customer);

//     response.addHeader("Authorization", "Bearer " + token);
//     response.getWriter().flush();

//     super.successfulAuthentication(request, response, chain, authResult);
//   }
// }
