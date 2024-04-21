package com.ihc.apirest.controllers;


import com.ihc.apirest.usecase.ProcessCategory;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class CategoryController 
{

  @Autowired
  ProcessCategory processCategory;
  

  /**
   * Método que permite otener todas las categorias con sus tiendas
   * @return Listado de categorias con tiendas
   */
  @GetMapping(value = "")
  public ResponseEntity<Map<String, Object>> getAllCategories() 
  {
    Map<String, Object> mapResponse = processCategory.getAllCategories();

    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }


  /**
   * Método que permite cargar las imagenes de categorias al repositorio
   * GUIA para correr este endpoint
   * 
   * 1. En el archivo "/Volumes/Datos/Proyectos/unicentro/cloudinary/load-images/categories_images.txt",
   *    colocar las imagenes que pertenecen a las categorias y hacen referencia a la tabla "appmall.categories"
   *
   * @return Listado con las urls de las imagenes cargadas
   */
  @GetMapping(value = "/loads/images")
  public ResponseEntity<Map<String, Object>> loadImagesCategories() 
  {
    Map<String, Object> mapResponse = processCategory.loadImagesCategories();

    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }
}
