package com.ihc.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihc.apirest.models.Category;
import com.ihc.apirest.repository.CategoryRepository;



@Service
public class CategoryService 
{

  @Autowired
  private CategoryRepository categoryRepository;


  
  /**
   * Método que permite otener todas las categorias con sus tiendas
   * @return Listado de categorias con tiendas
   */
  public List<Category> getAllCategories() throws Exception 
  {
    return categoryRepository.findAllByOrderByIdCategoryAsc();
  }
}
