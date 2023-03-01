package com.ihc.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihc.apirest.models.Promotion;
import com.ihc.apirest.repository.PromotionRepository;



@Service
public class PromotionService 
{

  @Autowired
  private PromotionRepository promotionRepository;

  
  /**
   * Método que permite otener todas las promociones
   * @return Listado de promociones
   */
  public List<Promotion> getAllPromotions() throws Exception 
  {
    return promotionRepository.findAll();
  }


  /**
   * Método que permite crear una promoción
   * @return Promoción creada
   */
  public Promotion createPromotion(Promotion promotion) throws Exception 
  {
    return promotionRepository.save(promotion);
  }


  /**
   * Método que permite eliminar todas las promociones
   */
  public void deletePromotion() throws Exception 
  {
    promotionRepository.deleteAll();
  }
}
