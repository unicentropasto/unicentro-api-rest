package com.ihc.apirest.usecase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ihc.apirest.models.Neighborhood;
import com.ihc.apirest.service.NeighborhoodService;
import com.ihc.apirest.utilities.Constants;


@Component
public class ProcessNeighborhood 
{

  @Autowired
  NeighborhoodService neighborhoodService;
  

  /**
   * Método que permite otener todos los barrios
   * @return Listado de barrios
   */
  public Map<String, Object> getAllNeighborhoods()
  {
    Map<String, Object> mapResponse = new HashMap<>();
    mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_ERROR_CODE);

    try
    {
      List<Neighborhood> lstNeighborhoods = neighborhoodService.getAllNeighborhoods();
      
      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_OK_CODE);
      mapResponse.put(Constants.RESPONSE_DATA, lstNeighborhoods);

      return mapResponse;
    }
    catch (Exception e) 
    {
      return mapResponse;
    }
  }
}
