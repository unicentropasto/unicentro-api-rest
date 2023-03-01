package com.ihc.apirest.usecase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ihc.apirest.models.VWIdentificationType;
import com.ihc.apirest.service.VWIdentificationTypeService;
import com.ihc.apirest.utilities.Constants;


@Component
public class ProcessVWIdentificationType 
{

  @Autowired
  VWIdentificationTypeService vwIdentificationTypeService;
  

  /**
   * Método que permite otener todos los tipos de identificación
   * @param groupCode Codigo de grupo
   * @return Listado de tipos de identificación
   */
  public Map<String, Object> getAllIdentificationsType()
  {
    Map<String, Object> mapResponse = new HashMap<>();

    try
    {
      List<VWIdentificationType> lstIdentificationsType = vwIdentificationTypeService.getAllIdentificationsType(Constants.GROPU_CODE);

      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_OK_CODE);
      mapResponse.put(Constants.RESPONSE_DATA, lstIdentificationsType);

      return mapResponse;
    }
    catch (Exception e) 
    {
      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_ERROR_CODE);
      return mapResponse;
    }
  }
}
