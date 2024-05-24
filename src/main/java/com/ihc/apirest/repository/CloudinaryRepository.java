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



  /**
   * Método que permite cargar imagenes al repositorio
   * @param imageName Nombre de la imagene a cargar
   * @return URL puplico de la imagen cargada, en caso contrario devuelve un mensaje indicando que no se pudo cargar
   */
  @Override
  public String loadImage(String imageName) throws Exception 
  {
    File file = null;
    String imageUrl = null;
    

    file = new File("/Volumes/Datos/Proyectos/unicentro/cloudinary/image-repository/" + imageName);

    if(file.exists())
    {
      // Creamos un map de opciones para la carga
      Map<String, Object> options = new HashMap<String, Object>();
      options.put("public_id", imageName.split("\\.")[0]);
      options.put("overwrite", true);
      options.put("resource_type", "image");
      options.put("folder", "image-repository");
  
      Map<String, Object> result = cloudinary.uploader().upload(file, options);
  
      imageUrl = result.get("secure_url").toString();
    }

    return imageUrl;
  }


  /**
   * Método que tengo que explorar
   */
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



  /**
   * Método que permite eliminar de forma masiva las imganes del respositorio
   * @return Mensaje indicando si fue exitoso o no el proceso de borrado
   */
  @Override
  public String deleteImage(List<String> lstImagesNames, Map<String, Object> mapParameters) throws Exception 
  {   
    ApiResponse apiResponse = cloudinary.api().deleteResources(lstImagesNames, mapParameters);
    return apiResponse.toString();
  }
}