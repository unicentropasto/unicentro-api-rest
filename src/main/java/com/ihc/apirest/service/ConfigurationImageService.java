package com.ihc.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihc.apirest.models.ConfigurationImage;
import com.ihc.apirest.repository.ConfigurationImageRepository;



@Service
public class ConfigurationImageService 
{

  @Autowired
  private ConfigurationImageRepository configurationImageRepository;


  
  /**
   * Método que permite otener toda la configuración de imágenes
   * @return Listado de configuración de imágen
   */
  public List<ConfigurationImage> geConfigurationImagesByType(String type) throws Exception 
  {
    return configurationImageRepository.findByType(type);
  }
}
