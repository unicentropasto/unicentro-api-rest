package com.ihc.apirest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihc.apirest.models.Role;
import com.ihc.apirest.repository.RoleRepository;



@Service
public class RoleService 
{

  @Autowired
  private RoleRepository roleRepository;


  
  /**
   * Método que permite otener un rol según su id
   * @return Role
   */
  public Role getRoleById(Long idRole) throws Exception 
  {
    return roleRepository.getReferenceById(idRole);
  }
}
