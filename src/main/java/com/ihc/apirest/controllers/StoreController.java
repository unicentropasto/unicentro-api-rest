package com.ihc.apirest.controllers;


import com.ihc.apirest.usecase.ProcessStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/stores")
@CrossOrigin("*")
public class StoreController 
{

  @Autowired
  ProcessStore processStore;
  

  /**
   * Método que permite otener todas las categorias con sus tiendas
   * GUIA para correr este endpoint
   * 
   * 1. Si no carga lo primero eliminar el archivo /Users/luisa/Trabajo_Ivan/Proyectos/unicentro/unicentro-api-rest-mall/tokens/StoredCredential
   * 
   * 2. Se vuelve a levantar el backend para obtener en la consola el link para authenticar con google y despues de loguearnos a google el back crea automaticamente este archivo
   * 
   * 3. Validar si el excel tiene 13 columnas o tiene filas vacias al final del excel
   * @return Listado de categorias con tiendas
   */
  @GetMapping(value = "/loads")
  public ResponseEntity<String> loadStores() 
  {
    try
    {
      String msj = processStore.loadStores();

      return new ResponseEntity<String>(msj, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<String>("En el momento no fue posible cargar el archivo de locales, favor intentarlo mas tarde.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
