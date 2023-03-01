package com.ihc.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihc.apirest.models.VWIdentificationType;
import com.ihc.apirest.repository.VWIdentificationTypeRepository;


@Service
public class VWIdentificationTypeService 
{
  
  @Autowired
  private VWIdentificationTypeRepository vwIdentificationTypeRepository;


  
  /**
   * Método que permite otener todos los tipos de identificación
   * @param groupCode Codigo de grupo
   * @return Listado de tipos de identificación
   */
  public List<VWIdentificationType> getAllIdentificationsType(String groupCode) throws Exception 
  {
    return vwIdentificationTypeRepository.findByGroupCode(groupCode);
  }
}
