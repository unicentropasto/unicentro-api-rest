package com.ihc.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihc.apirest.models.IdentificationType;
import com.ihc.apirest.repository.IdentificationTypeRepository;


@Service
public class IdentificationTypeService 
{
  
  @Autowired
  private IdentificationTypeRepository identificationTypeRepository;


  
  /**
   * Método que permite otener todos los tipos de identificación
   * @param groupCode Codigo de grupo
   * @return Listado de tipos de identificación
   */
  public List<IdentificationType> getAllIdentificationsType(String groupCode) throws Exception 
  {
    return identificationTypeRepository.findByGroupCode(groupCode);
  }
}
