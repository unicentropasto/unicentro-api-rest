package com.ihc.apirest.service;

import com.ihc.apirest.models.Customer;
import com.ihc.apirest.repository.CustomerRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class CustomerService 
{
  @Autowired
  private CustomerRepository customerRepository;



  /**
   * Método que permite registrar un cliente
   * @param customer Cliente a registrar
   * @return Cliente registrado
   */
  public Customer signUp(Customer customer) throws Exception 
  {
    return customerRepository.save(customer);
  }


  /**
   * Método que permite actualizar todo el cliente
   * @param customer Cliente a actualizar
   * @return Cliente actualizado
   */
  public Customer updateCustomer(Customer customer) throws Exception 
  {
    return customerRepository.save(customer);
  }
  
  
  /**
   * Método que permite actualizar el password del cliente
   * @param customer Cliente que contiene el password a actualizar
   * @return 1 si el password fue actualizado, en caso contrario 0
   */
  public Integer updatePasswordCustomer(Customer customer) throws Exception 
  {
    return customerRepository.updatePasswordCustomer(customer.getPassword(), customer.getIdCustomer());
  }


  /**
   * Método que permite restaurar temporalmente el password de un cliente
   * @param customer Cliente que contiene el password a restaurar
   * @param email Correo electrónico al cual se enviará el password temporal
   * @return 1 si el password fue restaurado y enviado al email, en caso contrario 0
   */
  public Integer restorePassword(String password, String email) throws Exception
  {
    return customerRepository.restorePassword(password, email);
  }


  /**
   * Método que permite obtener un cliente según su email
   * @param email Correo electrónico con el cual se buscara el cliente
   * @return Cliente encontrado
   */
  public Customer getCustomerByEmail(String email) throws UsernameNotFoundException 
  {
    return customerRepository.findByEmail(email);
  }


  /**
   * Método que permite obtener un cliente según su email
   * @param email Correo electrónico con el cual se buscara el cliente
   * @return Cliente encontrado
   */
  public Customer getCustomerByIdentificationDocument(String identificationDocument) throws Exception 
  {
    return customerRepository.findByIdentificationDocument(identificationDocument);
  }


  /**
   * Método que permite validar si un cliente existe en bd según su user_name(email)
   * @param email Correo electrónico que se va a valdiar
   * @return true si existe el email, en caso contrario false
   */
  public Boolean existsByEmail(String email) throws Exception
  {
    return customerRepository.existsByEmail(email);
  }


  /**
   * Método que permite obtener un cliente según su id
   * @param idCustomer Id con el cual se buscara el cliente
   * @return Cliente encontrado
   */
  public Optional<Customer> getCustomerById(Long idCustomer) throws UsernameNotFoundException 
  {
    return customerRepository.findById(idCustomer);
  }
}
