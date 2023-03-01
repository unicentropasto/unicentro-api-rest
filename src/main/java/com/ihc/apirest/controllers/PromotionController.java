package com.ihc.apirest.controllers;


import com.ihc.apirest.usecase.ProcessPromotion;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/promotions")
@CrossOrigin("*")
public class PromotionController 
{

  @Autowired
  ProcessPromotion processPromotion;
  

  /**
   * Método que permite otener todas las promociones
   * @return Listado de promociones
   */
  @GetMapping(value = "")
  public ResponseEntity<Map<String, Object>> getAllPromotions() 
  {
    Map<String, Object> mapResponse = processPromotion.getAllPromotions();

    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }


  /**
   * Método que permite cargar todas las promociones
   * @return Listado de promociones
   */
  @GetMapping(value = "/loads")
  public ResponseEntity<String> loadPromotions() 
  {
    try
    {
      String msj = processPromotion.loadPromotions();

      return new ResponseEntity<String>(msj, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<String>("En el momento no fue posible cargar el archivo de promociones, favor intentarlo mas tarde.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
