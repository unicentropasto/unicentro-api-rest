package com.ihc.apirest.usecase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihc.apirest.models.MenuOptionRole;
import com.ihc.apirest.models.Role;
import com.ihc.apirest.service.RoleService;
import com.ihc.apirest.utilities.Constants;



@Service
public class ProcessMenuOptionRole
{
  
  @Autowired
  private RoleService roleService;


  
  /**
   * Método que permite otener todas las opciones de menú según el rol
   * @param idRole Id del rol
   * @return Listado de opciones de menú
   */
  public Map<String, Object> getAllMenuOptionsByRole(Long idRole)
  {
    Map<String, Object> mapResponse = new HashMap<>();

    try
    {
      Role role = roleService.getRoleById(idRole);

      List<MenuOptionRole> lstMenuOptionsRole = role.getLstMenuOptionsRole().stream().filter(menuOptionRole -> menuOptionRole.getMenuOption().getIdState() == 1).collect(Collectors.toList());

      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_OK_CODE);
      mapResponse.put(Constants.RESPONSE_DATA, lstMenuOptionsRole);

      return mapResponse;
    }
    catch (Exception e) 
    {
      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_ERROR_CODE);
      return mapResponse;
    } 
  }
}
