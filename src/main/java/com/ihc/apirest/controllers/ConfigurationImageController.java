package com.ihc.apirest.controllers;


import com.ihc.apirest.usecase.ProcessConfigurationImage;

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
@RequestMapping("/configurations/images")
@CrossOrigin("*")
public class ConfigurationImageController 
{

  @Autowired
  ProcessConfigurationImage processConfigurationImage;
  

  /**
   * Método que permite otener toda la configuración de imágenes
   * @return Listado de configuración de imágen
   */
  @GetMapping(value = "/types/{type}")
  public ResponseEntity<Map<String, Object>> geConfigurationImagesByType(@PathVariable("type") String type)
  {
    Map<String, Object> mapResponse = processConfigurationImage.geConfigurationImagesByType(type);

    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }
}
