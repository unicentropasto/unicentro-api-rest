package com.ihc.apirest.controllers;


import com.ihc.apirest.service.CloudinaryService;
import com.ihc.apirest.usecase.ProcessConfigurationImage;

import java.util.ArrayList;
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
  CloudinaryService cloudinaryService;

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


  /**
   * Método que permite cargar imagenes de configuración a cloudinary
   * @return Listado de imágenes cargadas
   */
  @GetMapping(value = "/loads")
  public ResponseEntity<ArrayList<String>> loadImagesConfiguraions() 
  {
    try
    {
      ArrayList<String> lstImages = new ArrayList<>();
      ArrayList<String> lstImagesUrl = new ArrayList<>();

      lstImages.add("icon-tiendas-home.png");
      lstImages.add("icon-bancos-home.png");
      lstImages.add("icon-comidas-home.png");
      lstImages.add("icon-entretenimiento-home.png");
      lstImages.add("icon-02.png");
      lstImages.add("icon-03.png");
      lstImages.add("icon-04.png");
      lstImages.add("icon-05.png");
      lstImages.add("icon-06.png");
      lstImages.add("icon-07.png");

      lstImages.add("icon-sorteos.png");
      lstImages.add("banner-626x352-42.png");
      lstImages.add("banner-626x352-43.png");

      for (String imageName : lstImages) 
      {
        String imageUrl = cloudinaryService.loadImage(imageName);
        System.out.println(imageUrl);
        lstImagesUrl.add(imageUrl);
      }

      return new ResponseEntity<ArrayList<String>>(lstImagesUrl, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
