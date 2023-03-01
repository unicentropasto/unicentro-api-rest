package com.ihc.apirest.usecase;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.ihc.apirest.models.Customer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.ExpiredJwtException;
// import io.jsonwebtoken.MalformedJwtException;
// import io.jsonwebtoken.SignatureException;
// import io.jsonwebtoken.UnsupportedJwtException;



@Component
public class ProcessToken 
{

  @Value("${jwt.secret_key}")
  private String secretKey;

  @Value("${jwt.expiration}")
  private int expiration;


  
  /**
   * Método que permite crear un token a partir de la autenticación del customer (login y password)
   * @param customer Cliente que contiene roles y el username(email)
   * @return Token
   */
  public String createToken(Customer customer) 
  {
    // Parametros que se van a encapsular en el token
    Map<String, Object> params = new HashMap<>();
    params.put("idCustomer", (Long) customer.getIdCustomer());

    try
    {

      return Jwts.builder()
                  .setSubject(customer.getEmail())
                  // .setExpiration(new Date(60000))
                  .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                  .addClaims(params)
                  .setIssuedAt(new Date())
                  // .setExpiration(new Date(System.currentTimeMillis() + 10000))
                  .signWith(SignatureAlgorithm.HS512, secretKey)
                  .compact();
    }
    catch(JwtException e)
    {
      return null;
    }
  }


  
  /**
   * Método que permite obtener el username(email) a partir del token
   * @param token Contiene el username
   * @return Username
   */
  public UsernamePasswordAuthenticationToken getAuthentication(String token) 
  {
    try
    {
      Claims claims = Jwts.parser()
                          .setSigningKey(secretKey)
                          .parseClaimsJws(token)
                          .getBody();
      
      String email = claims.getSubject();

      return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
    }
    catch(JwtException e)
    {
      return null;
    }
  }


  /**
   * Método que permite validar el token
   * @param token a validar
   * @return true si el token es valido, en caso contrario false
   */
  // public boolean validateToken(String token)
  // {
  //   try 
  //   {
  //     Jwts.parser().setSigningKey(secretKey).parseClaimsJws(getToken(token));

  //     return true;

  //     //TODO:Generar Logger o registrar en bd el error
  //   } 
  //   catch (MalformedJwtException e) 
  //   {
  //     System.out.println("token mal formado");
  //   } 
  //   catch (UnsupportedJwtException e) 
  //   {
  //     System.out.println("token no soportado");
  //   } 
  //   catch (ExpiredJwtException e) 
  //   {
  //     System.out.println("token expirado");
  //   } 
  //   catch (IllegalArgumentException e) 
  //   {
  //     System.out.println("token vacío");
  //   } 
  //   catch (SignatureException e) 
  //   {
  //     System.out.println("fail en la firma");
  //   }
  //   return false;
  // }


  /**
   * Método que permite obtener el token a partir del header
   * @param headerAuthorization contiene atributo authorization
   * @return token
   */
  public String getToken(String headerAuthorization) 
  {
    if (null != headerAuthorization && headerAuthorization.startsWith("Bearer"))
      return headerAuthorization.replace("Bearer ", "");

    return null;
  }


  /**
   * Método que permite obtener el email a partir del token
   * @param headerAuthorization Token que contiene el email
   * @return Email
   */
  public String getEmailFromToken(String headerAuthorization) 
  {
    String token = getToken(headerAuthorization);

    try
    {
      return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    catch(JwtException e)
    {
      return null;
    }
  }


  /**
   * Método que permite obtener el id de cliente a partir del token
   * @param headerAuthorization Contiene el id del cliente
   * @return Id Customer
   */
  public Long getIdCustomerFromToken(String headerAuthorization) 
  {
    try
    {
      String token = getToken(headerAuthorization);

      Claims claims = Jwts.parser()
                          .setSigningKey(secretKey)
                          .parseClaimsJws(token)
                          .getBody();

      return Long.parseLong(claims.get("idCustomer").toString());
    }
    catch(JwtException e)
    {
      return null;
    }
  }
}
