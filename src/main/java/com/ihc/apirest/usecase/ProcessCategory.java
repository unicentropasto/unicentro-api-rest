package com.ihc.apirest.usecase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ihc.apirest.models.Category;
import com.ihc.apirest.service.CategoryService;
import com.ihc.apirest.service.CloudinaryService;
import com.ihc.apirest.utilities.Constants;


@Component
public class ProcessCategory 
{
  @Autowired
  CategoryService categoryService;


  @Autowired
  CloudinaryService cloudinaryService;


  /**
   * Método que permite otener todas las categorias con sus tiendas
   * @return Listado de categorias con tiendas
   */
  public  Map<String, Object> getAllCategories()
  {
    Map<String, Object> mapResponse = new HashMap<>();

    try
    {
      List<Category> lstCategories = categoryService.getAllCategories();

      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_OK_CODE);
      mapResponse.put(Constants.RESPONSE_DATA, lstCategories);

      return mapResponse;
    }
    catch (Exception e) 
    {
      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_ERROR_CODE);
      
      return mapResponse;
    }
  }


  /**
   * Método que permite cargar imagenes de forma masiva al repositorio
   * @return Listado de urls de las imagenes cargadas
   */
  public Map<String, Object> loadImagesCategories() 
  {
    Map<String, Object> mapResponse = new HashMap<>();

    // Variables para utilizar en el manejo del archivo
    FileInputStream fileInputStream = null;
    InputStreamReader inputStreamReader = null;
    BufferedReader bufferedReader = null;

    try
    {
      List<String> lstImagesCategories = new ArrayList<>();
      
      // Cargando las imagenes pertenecientes a las categorias referentes a la tabla "appmall.categories"
      File file = new File("/Volumes/Datos/Proyectos/unicentro/cloudinary/load-images/categories_images.txt");

      // Crear un FileInputStream para abrir el archivo
      fileInputStream = new FileInputStream(file);

      // Crear un InputStreamReader para leer bytes y convertirlos a caracteres
      inputStreamReader = new InputStreamReader(fileInputStream);

      // Crear un BufferedReader para leer líneas de texto eficientemente
      bufferedReader = new BufferedReader(inputStreamReader);

      // Variable para almacenar cada línea leída del archivo
      String imageName;

      // Leer cada línea del archivo hasta el final
      while ((imageName = bufferedReader.readLine()) != null) 
      {
        String urlImage = cloudinaryService.loadImage(imageName);
        lstImagesCategories.add(urlImage);
      }

      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_OK_CODE);
      mapResponse.put(Constants.RESPONSE_DATA, lstImagesCategories);

      return mapResponse;
    }
    catch (Exception e) 
    {
      mapResponse.put(Constants.RESPONSE_CODE, "Error al cargar las imagenes: " + e.getMessage());

      return mapResponse;
    }
    finally 
    {
      // Cerrar recursos para liberar memoria y evitar fugas de recursos
      try 
      {
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (fileInputStream != null) {
            fileInputStream.close();
        }
      } 
      catch (IOException e) 
      {
        System.out.println("Error al cerrar los recursos: " + e.getMessage());
      }
    }
  }
}
