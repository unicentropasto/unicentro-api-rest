package com.ihc.apirest.controllers;


import com.ihc.apirest.service.CloudinaryService;
import com.ihc.apirest.usecase.ProcessConfigurationImage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
  CloudinaryService cloudinaryService;

  @Autowired
  ProcessConfigurationImage processConfigurationImage;
  

  /**
   * Método que permite otener toda la configuración de imágenes
   * @return Listado de configuración de imágenes
   */
  @GetMapping(value = "/types/{type}")
  public ResponseEntity<Map<String, Object>> geConfigurationImagesByType(@PathVariable("type") String type)
  {
    Map<String, Object> mapResponse = processConfigurationImage.geConfigurationImagesByType(type);

    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }


  /**
   * Método que permite cargar la imagenes de configuración al repositorio
   * GUIA para correr este endpoint
   * 
   * 1. En el archivo "/Volumes/Datos/Proyectos/unicentro/cloudinary/load-images/configuration_images.txt",
   *    colocar las imagenes que pertenecen a la configuracion de imagenes y hacen referencia a la tabla "appmall.configuration_images"
   *
   * @return Listado con las urls de las imagenes cargadas
   */
  @GetMapping(value = "/loads")
  public ResponseEntity<Map<String, Object>> loadImagesConfiguraions() 
  {
    Map<String, Object> mapResponse = processConfigurationImage.loadImagesConfiguraions();

    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }

  
  /**
   * Método que permite eliminar de forma masiva las imagenes del respositorio
   * @return Mensaje indicando si fue exitoso o no el proceso de borrado
   */
  @DeleteMapping(value = "/deletes")
  public ResponseEntity<Map<String, Object>> deleteImage() 
  {
    Map<String, Object> mapResponse = processConfigurationImage.deleteImage();

    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }
}
