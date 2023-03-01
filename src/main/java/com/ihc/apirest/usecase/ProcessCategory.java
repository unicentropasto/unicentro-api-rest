package com.ihc.apirest.usecase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ihc.apirest.models.Category;
import com.ihc.apirest.service.CategoryService;
import com.ihc.apirest.utilities.Constants;


@Component
public class ProcessCategory 
{
  @Autowired
  CategoryService categoryService;


  /**
   * Método que permite otener todas las categorias con sus tiendas
   * @return Listado de categorias con tiendas
   */
  public  Map<String, Object> getAllCategories()
  {
    Map<String, Object> mapResponse = new HashMap<>();

    try
    {
      List<Category> lstCategories = categoryService.getAllCategories();

      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_OK_CODE);
      mapResponse.put(Constants.RESPONSE_DATA, lstCategories);

      return mapResponse;
    }
    catch (Exception e) 
    {
      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_ERROR_CODE);
      
      return mapResponse;
    }
  }
}
