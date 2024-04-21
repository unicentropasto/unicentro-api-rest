package com.ihc.apirest.usecase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ihc.apirest.models.ConfigurationImage;
import com.ihc.apirest.service.CloudinaryService;
import com.ihc.apirest.service.ConfigurationImageService;
import com.ihc.apirest.utilities.Constants;


@Component
public class ProcessConfigurationImage 
{
  @Autowired
  ConfigurationImageService configurationImageService;

  @Autowired
  CloudinaryService cloudinaryService;


  /**
   * Método que permite otener toda la configuración de imágenes
   * @return Listado de configuración de imágen
   */
  public Map<String, Object> geConfigurationImagesByType(String type)
  {
    Map<String, Object> mapResponse = new HashMap<>();

    try
    {
      List<ConfigurationImage> lstConfigurationImages = configurationImageService.geConfigurationImagesByType(type);

      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_OK_CODE);
      mapResponse.put(Constants.RESPONSE_DATA, lstConfigurationImages);

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
  public Map<String, Object> loadImagesConfiguraions() 
  {
    Map<String, Object> mapResponse = new HashMap<>();

    // Variables para utilizar en el manejo del archivo
    FileInputStream fileInputStream = null;
    InputStreamReader inputStreamReader = null;
    BufferedReader bufferedReader = null;

    try
    {
      List<String> lstImagesConfigurations = new ArrayList<>();

      // En este archivo se debe colocar el nombre de la imagen ubicada en la colulma IMAGEN LOGO	oh IMAGEN FACHADA del excel
      // pertenecientes a la tabla "configuration_images"
      File file = new File("/Volumes/Datos/Proyectos/unicentro/cloudinary/load-images/configuration_images.txt");

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
        lstImagesConfigurations.add(urlImage);
      }

      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_OK_CODE);
      mapResponse.put(Constants.RESPONSE_DATA, lstImagesConfigurations);

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


  /**
   * Método que permite eliminar de forma masiva las imganes del respositorio
   * @return Mensaje indicando si fue exitoso o no el proceso de borrado
   * @throws IOException
   * @throws GeneralSecurityException
   * @throws Exception
   */
  public Map<String, Object> deleteImage()
  {
    Map<String, Object> mapResponse = new HashMap<>();

    // Variables para utilizar en el manejo del archivo
    FileInputStream fileInputStream = null;
    InputStreamReader inputStreamReader = null;
    BufferedReader bufferedReader = null;
    
    Map<String, Object> mapParameters = new HashMap<String, Object>();
    
    mapParameters.put("invalidate", true);
    mapParameters.put("type", "upload");
    mapParameters.put("resource_type", "image");

    List<String> lstImagesNames = new ArrayList<>();

    try 
    {
      // En este archivo se debe colocar el url de la imagen del repositorio cloudinary (me paro sobre la imagen y hago clic en el tac <> copy link y lo pego en ese archivo)
      File file = new File("/Volumes/Datos/Proyectos/unicentro/cloudinary/image-to-update/images.txt");

      // Crear un FileInputStream para abrir el archivo
      fileInputStream = new FileInputStream(file);

      // Crear un InputStreamReader para leer bytes y convertirlos a caracteres
      inputStreamReader = new InputStreamReader(fileInputStream);

      // Crear un BufferedReader para leer líneas de texto eficientemente
      bufferedReader = new BufferedReader(inputStreamReader);

      // Variable para almacenar cada línea leída del archivo
      String line;

      // Leer cada línea del archivo hasta el final
      while ((line = bufferedReader.readLine()) != null) 
      {
        String[] arrayLine = line.split("/");
        // Se obtiene el nombre de la imagen sin extension
        lstImagesNames.add(arrayLine[7] + "/" +arrayLine[8].split("\\.")[0]);
      }

      String msj = cloudinaryService.deleteImage(lstImagesNames, mapParameters);

      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_OK_CODE);
      mapResponse.put(Constants.RESPONSE_DATA, msj);

      return mapResponse;
    } 
    catch (Exception e) 
    {
      mapResponse.put(Constants.RESPONSE_CODE, "Error al eliminar las imagenes: " + e.getMessage());
      
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
