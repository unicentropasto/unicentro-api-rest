package com.ihc.apirest.controllers;


import com.ihc.apirest.usecase.ProcessMenuOptionRole;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/menuoptions")
@CrossOrigin("*")
public class MenuOptionRoleController 
{

  @Autowired
  ProcessMenuOptionRole processMenuOptionRole;


  /**
   * Método que permite otener todas las opciones de menú según el rol
   * @param idRole Id del rol
   * @return Listado de opciones de menú
   */
  @GetMapping(value = "/{idRole}")
  public ResponseEntity<Map<String, Object>> getAllMenuOptionsByRole(@PathVariable("idRole") Long idRole) 
  {
    Map<String, Object> mapResponse = processMenuOptionRole.getAllMenuOptionsByRole(idRole);

    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }
}
