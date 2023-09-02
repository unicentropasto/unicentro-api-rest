package com.ihc.apirest.controllers;

import com.ihc.apirest.models.Customer;
import com.ihc.apirest.usecase.ProcessCustomer;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/customers")
@CrossOrigin("*")
public class CustomerRestController 
{
  
  @Autowired
  ProcessCustomer processCustomer;
  

  /**
   * Método que permite registrar un cliente
   * @param customer Cliente a registrar
   * @return true si el cliente fue registrado correctamente, en caso contrario false
   */
  @PostMapping(value="/signup")
  public ResponseEntity<Map<String, Object>> signUp(@RequestBody Customer customer)
  {
    Map<String, Object> mapResponse = processCustomer.signUp(customer);
  
    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }


  /**
   * Método que permite validar un cliente según su correo y clave
   * @param customer Cliente que contiente el correo y clave a validar
   * @return Cliente encontrado
   */
  @PostMapping(value = "/signin")
  public ResponseEntity<Map<String, Object>> signIn(@RequestBody Customer customer) 
  {
    Map<String, Object> mapResponse = processCustomer.signIn(customer);

    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }


  /**
   * Método que permite validar el token
   * @param headerAuthorization Contiene el token
   * @return true si el token es valido, en caso contrario false
   */
  @GetMapping(value = "/validations/tokens")
  public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String headerAuthorization) 
  {
    Map<String, Object> mapResponse = processCustomer.validateToken(headerAuthorization);

    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }


  /**
   * Método que permite actualizar los datos de un cliente
   * @param customer Clienta a actualizar
   * @param headerAuthorization Contiene el token
   * @return true si el cliente fue actualizado correctamente, en caso contrario false
   */
  @PutMapping("")
  public ResponseEntity<Map<String, Object>> updateCustomer(@RequestBody Customer customer, @RequestHeader("Authorization") String headerAuthorization)
  {
    Map<String, Object> mapResponse = processCustomer.updateCustomer(customer, headerAuthorization);

    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }



  /**
   * Método que permite actualizar el password del cliente
   * @param customer Cliente al cual se le actualizará el password
   * @param headerAuthorization Contiene el token
   * @return true si el password del cliente fue actualizado correctamente, en caso contrario false
   */
  @PutMapping("/passwords")
  public ResponseEntity<Map<String, Object>> updatePasswordCustomer(@RequestBody Customer customer, @RequestHeader("Authorization") String headerAuthorization)
  {
    Map<String, Object> mapResponse = processCustomer.updatePasswordCustomer(customer, headerAuthorization);

    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }

  
  /**
   * Método que permite obtener el nombre del cliente a partir del token
   * @param headerAuthorization Contiene el token
   * @return Cliente encontrado
   */
  @GetMapping(value = "/names")
  public ResponseEntity<Map<String, Object>> getCustomerName(@RequestHeader("Authorization") String headerAuthorization) 
  {
    Map<String, Object> mapResponse = processCustomer.getCustomerName(headerAuthorization);
  
    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }


  /**
   * Método que permite obtener el cliente a partir del token
   * @param headerAuthorization Contiene el token
   * @return Cliente encontrado
   */
  @GetMapping(value = "")
  public ResponseEntity<Map<String, Object>> getCustomer(@RequestHeader("Authorization") String headerAuthorization) 
  {
    Map<String, Object> mapResponse = processCustomer.getCustomer(headerAuthorization);
  
    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }


  /**
   * Método que permite obtener el cliente a partir de su documento de identificación
   * @param identificationDocument Documento de identificación
   * @return Cliente encontrado
   */
  // @GetMapping(value = "/{identificationDocument}")
  // public ResponseEntity<Map<String, Object>> getCustomerByIdentificationDocument(@PathVariable("identificationDocument") String identificationDocument) 
  // {
  //   Map<String, Object> mapResponse = processCustomer.getCustomerByIdentificationDocument(identificationDocument);
  
  //   return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  // }
  
  
  /**
   * Método que permite restaurar el password de un cliente
   * @param headerAuthorization Contiene el token
   * @return true si se envió el correo correctamente, en caso contrario false
   */
  @PutMapping(value = "/restores")
  public ResponseEntity<Boolean> restorePassword(@RequestHeader("Authorization") String headerAuthorization)
  {
    try
    {
      Boolean isSent = processCustomer.restorePassword(headerAuthorization);
      
      return new ResponseEntity<Boolean>(isSent, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  /**
   * Método que permite eliminar la cuenta del cliente de la aplicación de manera lógica, no es un borrado físico en la bd
   * @param headerAuthorization Contiene el token
   * @return true si la eliminación lógica fue exitosa, en caso contrario false
   */
  @PutMapping(value = "/delete/accounts")
  public ResponseEntity<Map<String, Object>> deleteAccount(@RequestHeader("Authorization") String headerAuthorization)
  {
    Map<String, Object> mapResponse = processCustomer.deleteAccount(headerAuthorization);
  
    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }
}

