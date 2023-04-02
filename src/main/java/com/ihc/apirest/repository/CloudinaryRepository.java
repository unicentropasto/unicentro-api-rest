package com.ihc.apirest.repository;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.ihc.apirest.service.CloudinaryService;


@Repository
public class CloudinaryRepository implements CloudinaryService
{

  // Bean creado en la clase CloudinaryConfig
  @Autowired
  private Cloudinary cloudinary;


  @Override
  public String loadImage(String imageName) throws Exception 
  {
    File file = null;
    String imageUrl = null;
    
    try 
    {
      file = new File("/Users/luisa/Trabajo_Ivan/Proyectos/unicentro/mockups/cloudinary/" + imageName);

      // Creamos un map de opciones para la carga
      Map<String, Object> options = new HashMap<String, Object>();
      options.put("public_id", imageName.split("\\.")[0]);
      options.put("overwrite", true);
      options.put("resource_type", "image");
      options.put("folder", "image-repository");
  
      Map<String, Object> result = cloudinary.uploader().upload(file, options);
  
      imageUrl = result.get("secure_url").toString();
    } 
    catch (Exception e) 
    {
      System.out.println("Imagen picha: " + imageName);
    }

    return imageUrl;
  }


  @Override
  public Map<String, String> getAllImageUrl() throws Exception 
  {
    Map<String, String> mapImagesUrl = new HashMap<String, String>();

    // Búsqueda en cloudinary todos los recursos tipo imagen
    ApiResponse result = cloudinary.search().expression("resource_type:image").execute();

    List<Map<?, ?>> listResources =  (List<Map<?, ?>>) result.get("resources");

    for(Map<?, ?> mapResource : listResources) 
    {
      mapImagesUrl.put(mapResource.get("filename").toString(), mapResource.get("secure_url").toString());
    }

    return mapImagesUrl;
  }
}
