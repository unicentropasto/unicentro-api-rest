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
   * Método que permite cargar las promociones
   * GUIA para correr este endpoint
   * 
   * 1. Si no carga el proyecto, entonces lo primero eliminar el archivo /Volumes/Datos/Proyectos/unicentro-api-rest/tokens/StoredCredential
   * 
   * 2. Se vuelve a levantar el backend para obtener en la consola el link para authenticar con google y despues de loguearnos a google el back crea automaticamente este archivo
   * 
   * 3. Validar si el excel tiene 6 columnas o tiene filas vacias al final del excel
   * @return Listado de promociones
   */
  @GetMapping(value = "/loads/images")
  public ResponseEntity<Map<String, Object>> loadPromotions() 
  {
    Map<String, Object> mapResponse = processPromotion.loadPromotions();

    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }
}
