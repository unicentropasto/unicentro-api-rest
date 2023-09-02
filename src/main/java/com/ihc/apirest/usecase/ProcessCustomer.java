package com.ihc.apirest.usecase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.text.SimpleDateFormat;

import com.ihc.apirest.models.Customer;

import com.ihc.apirest.service.CustomerService;
import com.ihc.apirest.utilities.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.Authentication;

import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.CreateSmtpEmail;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailTo;




@Component
public class ProcessCustomer 
{
  @Autowired
  CustomerService customerService;

  @Autowired
  ProcessToken processToken;

  @Autowired
  BCryptPasswordEncoder bcrypt;

  @Autowired
  AuthenticationManager authenticationManager;

  SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");



  /**
   * Método que permite registrar un cliente
   * @param customer Cliente a registrar
   * @return true si el cliente fue registrado correctamente, en caso contrario false
   */
  public Map<String, Object> signUp(Customer customer) 
  {
    Map<String, Object> mapResponse = new HashMap<>();

    try
    {
      Customer customerBD = customerService.getCustomerByIdentificationDocument(customer.getIdentificationDocument());

      boolean isExistEmail = false;

      customer.setPassword(bcrypt.encode(customer.getPassword()));

      // Si el cliente es nuevo validamos que el email no exista, en caso contrario validamos si el email que se encuentra registrado es diferente y en caso de serlo validamos que no exista en el sistema
      if(null == customerBD)
      {
        isExistEmail = customerService.existsByEmail(customer.getEmail());
      }
      else
      {
        customer.setIdCustomer(customerBD.getIdCustomer());
        customer.setCustomerType(customerBD.getCustomerType() + "-" + customer.getCustomerType());

        // Si el cliente esta en sisbol y su correo de sisbol es diferente al que esta ingresando, entonces validamos que no exita el nuevo correo en el sistema
        if(!customer.getEmail().equals(customerBD.getEmail()))
        {
          isExistEmail = customerService.existsByEmail(customer.getEmail());
        }

        // Toda actualización de password se debe hacer individual, el password no se actualiza con un save ya que contiene la propiedad updatable = false en el entity Customer
        customerService.updatePasswordCustomer(customer);
      }

      //Se valida que el email del cliente no este registrado en la plataforma
      if(isExistEmail)
      {
        mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_FAILED_VALIDATION_CODE);
        
        return mapResponse;
      }


      //Este metodo creará un cliente en la plataforma
      Customer customerRegistered = customerService.signUp(customer);

      String token = processToken.createToken(customerRegistered);

      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_OK_CODE);
      mapResponse.put(Constants.RESPONSE_TOKEN, token);

      return mapResponse;
    }
    catch (Exception e) 
    {
      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_ERROR_CODE);
      return mapResponse;
    }
  }


  /**
   * Método que permite validar un cliente según su correo y clave
   * @param customer Cliente que contiente el correo y clave a validar
   * @return Cliente encontrado
   */
  public Map<String, Object> signIn(Customer customer)
  {
    Map<String, Object> mapResponse = new HashMap<>();

    try
    {
      //El documento de identificación es el username de la aplicacion
      //Se valida autenticación por medio de el documento de identificación y clave
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customer.getIdentificationDocument(), customer.getPassword()));
        
      SecurityContextHolder.getContext().setAuthentication(authentication);
      
      Customer customerBD = (Customer) authentication.getPrincipal();

      String token = processToken.createToken(customerBD);

      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_OK_CODE);
      mapResponse.put(Constants.RESPONSE_TOKEN, token);

      return mapResponse;
    }
    catch (BadCredentialsException | UsernameNotFoundException bce)
    {
      // El cliente no esta autorizado para acceder a la bd o su token expiro
      mapResponse.put(Constants.RESPONSE_CODE, Constants.NON_AUTHORITATIVE_CUSTOMER); 
      return mapResponse;
    }
    catch (Exception e) 
    {
      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_ERROR_CODE);
      return mapResponse;
    }
  }


  /**
   * Método que permite validar el token, no se necesita hacer validaciones adicionales en código ya que
   * la clase WebSecurityConfig se encarga automaticamente de validar el token, si esta bueno entra a este
   * endpoint y devuelve true, en caso contrario en donde el token sea invalido la clase WebSecurityConfig no
   * permite el acceso a este endpoint
   * @param headerAuthorization Contiene el token
   * @return True si el token es valido, en caso contrario False
   */
  public Map<String, Object> validateToken(String headerAuthorization)
  {
    Map<String, Object> mapResponse = new HashMap<>();

    mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_OK_CODE);

    return mapResponse;
  }


  /**
   * Método que permite actualizar los datos de un cliente
   * @param customer Clienta a actualizar
   * @return true si el cliente fue actualizado correctamente, en caso contrario false
   */
  public Map<String, Object> updateCustomer(Customer customer, String headerAuthorization)
  {
    Map<String, Object> mapResponse = new HashMap<>();
    boolean isExistEmail = false;

    try
    {
      // Se obtiene el parametro idCustomer de la propiedad claims del token
      Long idCustomer = processToken.getIdCustomerFromToken(headerAuthorization);

      // Obteniendo el cliente apartir del idCustomer
      Optional<Customer> customerBD = customerService.getCustomerById(idCustomer);


      if(null != idCustomer)
      {
        if(!customer.getEmail().equals(customerBD.get().getEmail()))
        {
          isExistEmail = customerService.existsByEmail(customer.getEmail());
        }

        if(isExistEmail)
        {
          mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_FAILED_VALIDATION_CODE);

          return mapResponse;
        }
        else
        {
          customer.setIdCustomer(idCustomer);

          if(!customer.getEmail().equals(customerBD.get().getEmail()))
          {
            String token = processToken.createToken(customer);
            mapResponse.put(Constants.RESPONSE_TOKEN, token);
          }
          customerService.updateCustomer(customer);
          
          mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_OK_CODE);
        }
      }

      return mapResponse;
    }
    catch (Exception e) 
    {
      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_ERROR_CODE);
      
      return mapResponse;
    }
  }


  /**
   * Método que permite actualizar el password del cliente
   * @param customer Cliente al cual se le actualizará el password
   * @param headerAuthorization Contiene el token
   * @return true si el password del cliente fue actualizado correctamente, en caso contrario false
   */
  public Map<String, Object> updatePasswordCustomer(Customer customer, String headerAuthorization)
  { 
    Map<String, Object> mapResponse = new HashMap<>();

    try
    {
      // Se obtiene el parametro idCustomer de la propiedad claims del token
      Long idCustomer = processToken.getIdCustomerFromToken(headerAuthorization);

      if(null != idCustomer)
      {
        customer.setIdCustomer(idCustomer);

        if(null == customer.getPassword())
        {
          customer.setPassword(null);
        }
        else
        {
          customer.setPassword(bcrypt.encode(customer.getPassword()));
        }
          
        customerService.updatePasswordCustomer(customer);
        
        mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_OK_CODE);
      }

      return mapResponse;
    }
    catch (Exception e) 
    {
      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_ERROR_CODE);
      
      return mapResponse;
    }
  }


  /**
   * Método que permite obtener el nombre del cliente a partir del token
   * @param headerAuthorization Contiene el token
   * @return Cliente encontrado
   */
  public Map<String, Object> getCustomerName(String headerAuthorization)
  {
    Map<String, Object> mapResponse = new HashMap<>();

    try
    {
      // Se obtiene el parametro idCustomer de la propiedad claims del token
      Long idCustomer = processToken.getIdCustomerFromToken(headerAuthorization);

      Optional<Customer> customer = customerService.getCustomerById(idCustomer);

      if(customer.isPresent())
      {
        mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_OK_CODE);
        mapResponse.put(Constants.RESPONSE_DATA, customer.get().getFirstName().concat("-").concat(customer.get().getGender()));
      } 
      else 
      {
        mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_ERROR_CODE);
      }

      return mapResponse;
    }
    catch (Exception e) 
    {
      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_ERROR_CODE);
      return mapResponse;
    }
  }


  /**
   * Método que permite obtener el cliente a partir del token
   * @param headerAuthorization Contiene el token
   * @return Cliente encontrado
   */
  public Map<String, Object> getCustomer(String headerAuthorization)
  {
    Map<String, Object> mapResponse = new HashMap<>();

    try
    {
      // Se obtiene el parametro idCustomer de la propiedad claims del token
      Long idCustomer = processToken.getIdCustomerFromToken(headerAuthorization);

      Optional<Customer> customer = customerService.getCustomerById(idCustomer);

      if (customer.isPresent())
      {
        customer.get().setPassword(null);
        mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_OK_CODE);
        mapResponse.put(Constants.RESPONSE_DATA, customer.get());
      } 
      else 
      {
        mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_ERROR_CODE);
      }

      return mapResponse;
    }
    catch (Exception e) 
    {
      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_ERROR_CODE);
      return mapResponse;
    }
  }


  /**
   * Método que permite obtener el cliente a partir del token
   * @param headerAuthorization Contiene el token
   * @return Cliente encontrado
   */
  // public Map<String, Object> getCustomerByIdentificationDocument(String identificationDocument)
  // {
  //   Map<String, Object> mapResponse = new HashMap<>();

  //   try
  //   {
  //     Customer customerBD = customerService.getCustomerByIdentificationDocument(identificationDocument);

  //     mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_ERROR_CODE);

  //     if(null != customerBD)
  //     {
  //       String token = processToken.createToken(customerBD);

  //       mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_OK_CODE);
  //       mapResponse.put(Constants.RESPONSE_TOKEN, token);
  //       // mapResponse.put(Constants.RESPONSE_DATA, customerBD); //si se define con miguel q se carguen los datos en el formulario de registro o se redirecciona al home
  //     }

  //     return mapResponse;
  //   }
  //   catch (Exception e) 
  //   {
  //     mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_ERROR_CODE);
  //     return mapResponse;
  //   }
  // }


  /**
   * Método que permite restaurar el password de un cliente
   * @param headerAuthorization Contiene el token
   * @return true si se envió el correo correctamente, en caso contrario false
   */
  public Boolean restorePassword(String headerAuthorization) throws Exception
  {
    // Se obtiene el email del cliente para enviar el password temporal
    // String token = jwtService.getToken(headerAuthorization);
    // String customerEmail = jwtService.getUserNameFromToken(token);

    // String passwordRandom = "1234";

    // passwordRandom = bcrypt.encode(passwordRandom);

    // customerService.restaurarPassword(passwordRandom, customerEmail);

    // String YOUR_DOMAIN_NAME = "sandboxa3bb8428392a4b859f2af588ec5feb87.mailgun.org";
    String API_KEY = "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";

    ApiClient apiClient = Configuration.getDefaultApiClient();
      
    // Configure API key authorization: api-key
    ApiKeyAuth apiKey = (ApiKeyAuth) apiClient.getAuthentication("api-key");
    apiKey.setApiKey(API_KEY);
    
    
    List<SendSmtpEmailTo> toList = new ArrayList<SendSmtpEmailTo>();
    SendSmtpEmailTo to = new SendSmtpEmailTo();
    to.setEmail("pandivan@hotmail.com"); //customerEmail
    // to.setName("Rana"); quitarlo tambien de la plantilla
    toList.add(to);
  
    Properties params = new Properties();
    params.setProperty("ORDER", "000007");
    params.setProperty("DATE", "19-03-2021");
    
    SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
    sendSmtpEmail.setTo(toList);
    sendSmtpEmail.setParams(params);
    sendSmtpEmail.setTemplateId(1L);

    TransactionalEmailsApi api = new TransactionalEmailsApi();
    CreateSmtpEmail response = api.sendTransacEmail(sendSmtpEmail);
    
    System.out.println(response.toString());

    return (null != response) ? true : false;
  }


  /**
   * Método que permite eliminar la cuenta del cliente de la aplicación de manera lógica, no es un borrado físico en la bd
   * @param headerAuthorization Contiene el token
   * @return true si la eliminación lógica fue exitosa, en caso contrario false
   */
  public Map<String, Object> deleteAccount(String headerAuthorization)
  { 
    Map<String, Object> mapResponse = new HashMap<>();

    try
    {
        return updatePasswordCustomer(new Customer(), headerAuthorization);
    }
    catch (Exception e) 
    {
      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_ERROR_CODE);
      
      return mapResponse;
    }
  }
}
