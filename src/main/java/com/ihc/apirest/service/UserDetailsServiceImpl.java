package com.ihc.apirest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService 
{
  @Autowired
  private CustomerService customerService;



  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
  {
    //El correo es el username de la aplicacion
    UserDetails userDetails = customerService.getCustomerByEmail(email);

    if (null == userDetails)
    {
      throw new UsernameNotFoundException("Cliente no registrado");
    }

    return userDetails;
  }
}
