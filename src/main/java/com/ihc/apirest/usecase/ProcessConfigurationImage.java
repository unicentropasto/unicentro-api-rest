package com.ihc.apirest.usecase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ihc.apirest.models.ConfigurationImage;
import com.ihc.apirest.service.ConfigurationImageService;
import com.ihc.apirest.utilities.Constants;


@Component
public class ProcessConfigurationImage 
{
  @Autowired
  ConfigurationImageService configurationImageService;


  /**
   * Método que permite otener toda la configuración de imágenes
   * @return Listado de configuración de imágen
   */
  public  Map<String, Object> geConfigurationImagesByType(String type)
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
}
