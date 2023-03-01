package com.ihc.apirest.controllers;


import com.ihc.apirest.usecase.ProcessVWIdentificationType;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/identificationstype")
@CrossOrigin("*")
public class VWIdentificationTypeController 
{

  @Autowired
  ProcessVWIdentificationType processVWIdentificationType;


  /**
   * Método que permite otener todos los tipos de identificación
   * @return Listado de tipos de identificación
   */
  @GetMapping(value = "")
  public ResponseEntity<Map<String, Object>> getAllIdentificationsType() 
  {
    Map<String, Object> mapResponse = processVWIdentificationType.getAllIdentificationsType();
  
    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }
}
