package com.ihc.apirest.usecase;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.ihc.apirest.models.Customer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;



@Component
public class ProcessToken 
{

  @Value("${jwt.secret_key}")
  private String secretKey;

  @Value("${jwt.expiration}")
  private int expiration;


  
  /**
   * Método que permite crear un token a partir de la autenticación del customer (login y password)
   * @param customer Cliente que contiene roles y el username(identificationDocument)
   * @return Token
   */
  public String createToken(Customer customer) 
  {
    // Parametros que se van a encapsular en el token
    Map<String, Object> params = new HashMap<>();
    params.put("idCustomer", (Long) customer.getIdCustomer());

    try
    {
      String token = Jwts.builder()
                        .subject(customer.getIdentificationDocument())
                        .expiration(new Date(new Date().getTime() + expiration * 1000))
                        .claims(params)
                        .issuedAt(new Date())
                        .signWith(key(), Jwts.SIG.HS256)
                        .compact();

      return token;
    }
    catch(JwtException e)
    {
      return null;
    }
  }


  
  /**
   * Método que permite obtener el username(identificationDocument) a partir del token
   * @param token Contiene el username
   * @return Username
   */
  public UsernamePasswordAuthenticationToken getAuthentication(String token) 
  {
    try
    {
      String identificationDocument = Jwts.parser()
                                          .verifyWith(key())
                                          .build()
                                          .parseSignedClaims(token)
                                          .getPayload()
                                          .getSubject();

      return new UsernamePasswordAuthenticationToken(identificationDocument, null, Collections.emptyList());
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
   * Método que permite obtener el documento de identificación a partir del token
   * @param headerAuthorization Token que contiene el documento de identificación
   * @return Documento de identificación
   */
  public String getIdentificationDocumentFromToken(String headerAuthorization) 
  {
    String token = getToken(headerAuthorization);

    try
    {
      String identificationDocument = Jwts.parser()
                                          .verifyWith((SecretKey) key())
                                          .build()
                                          .parseSignedClaims(token)
                                          .getPayload()
                                          .getSubject();
      
      return identificationDocument;
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
                          .verifyWith((SecretKey) key())
                          .build()
                          .parseSignedClaims(token)
                          .getPayload();

      return Long.parseLong(claims.get("idCustomer").toString());
    }
    catch(JwtException e)
    {
      return null;
    }
  }



  /**
   * Método que permite convertir el secretKey en bytes
   * @return El secretJey en bythes
   */
  private SecretKey key()
  {
      return Keys.hmacShaKeyFor(secretKey.getBytes());
  }
}
