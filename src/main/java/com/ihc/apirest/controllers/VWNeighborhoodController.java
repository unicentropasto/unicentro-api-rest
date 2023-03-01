package com.ihc.apirest.controllers;


import com.ihc.apirest.usecase.ProcessVWNeighborhood;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/neighborhoods")
@CrossOrigin("*")
public class VWNeighborhoodController 
{

  @Autowired
  ProcessVWNeighborhood processVWNeighborhood;


  /**
   * Método que permite otener todos los barrios
   * @return Listado de barrios
   */
  @GetMapping(value = "")
  public ResponseEntity<Map<String, Object>> getAllNeighborhoods() 
  {
    Map<String, Object> mapResponse = processVWNeighborhood.getAllNeighborhoods();

    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }
}
