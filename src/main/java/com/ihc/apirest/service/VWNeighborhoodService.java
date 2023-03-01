package com.ihc.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihc.apirest.models.VWNeighborhood;
import com.ihc.apirest.repository.VWNeighborhoodRepository;



@Service
public class VWNeighborhoodService 
{

  @Autowired
  private VWNeighborhoodRepository vwNeighborhoodRepository;


  
  /**
   * Método que permite otener todos los barrios
   * @return Listado de barrios
   */
  public List<VWNeighborhood> getAllNeighborhoods() throws Exception 
  {
    return vwNeighborhoodRepository.findAll();
  }
}
