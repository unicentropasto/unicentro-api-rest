package com.ihc.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihc.apirest.models.Neighborhood;
import com.ihc.apirest.repository.NeighborhoodRepository;



@Service
public class NeighborhoodService 
{

  @Autowired
  private NeighborhoodRepository neighborhoodRepository;


  
  /**
   * Método que permite otener todos los barrios
   * @return Listado de barrios
   */
  public List<Neighborhood> getAllNeighborhoods() throws Exception 
  {
    return neighborhoodRepository.findAll();
  }
}
