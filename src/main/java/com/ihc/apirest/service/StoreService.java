package com.ihc.apirest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihc.apirest.models.Store;
import com.ihc.apirest.repository.StoreRepository;



@Service
public class StoreService 
{

  @Autowired
  private StoreRepository storeRepository;


  
  /**
   * Método que permite crear un local
   * @return Local creado
   */
  public Store createStore(Store store) throws Exception 
  {
    return storeRepository.save(store);
  }


  /**
   * Método que permite obtener el id local según el número de local
   * @return Local
   */
  public Store getIdStoreByStoreNumber(String storeNumber) throws Exception 
  {
    return storeRepository.findByStoreNumber(storeNumber);
  }
}
