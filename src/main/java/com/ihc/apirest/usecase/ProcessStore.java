package com.ihc.apirest.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ihc.apirest.models.Store;
import com.ihc.apirest.service.GoogleService;
import com.ihc.apirest.service.StoreService;
import com.ihc.apirest.utilities.Constants;
import com.ihc.apirest.utilities.GenericFunctions;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.services.drive.model.File;


@Component
public class ProcessStore 
{
  @Autowired
  GoogleService googleService;

  @Autowired
  StoreService storeService;


  /**
   * Método que permite otener todas las promociones
   * @return Listado de promociones
   */
  // public List<Store> getAllStores() throws Exception 
  // {
  //   return promotionService.getAllStores();
  // }


  /**
   * Método que permite cargar los locales desde el repositorio google drive
   * @return Lista de locales
   * @throws IOException
   * @throws GeneralSecurityException
   * @throws Exception
   */
  public String loadStores() throws IOException, GeneralSecurityException, Exception
  {
    List<List<String>> lstDataExcel =  googleService.loadDataFileGoogleDrive("INFO WEB TIENDAS 2022.xlsx", "TIENDAS APP", 13);

    List<Store> lstStores = new ArrayList<>();

    for (List<String> lstRowExcel : lstDataExcel) 
    {
          System.out.println(lstRowExcel.get(1));
      // La columna 10 del excel representa el nombre de la imagen que se debe buscar en el drive de google para obtener su ID
      File fileImage = "".equals(lstRowExcel.get(11)) ? null : googleService.getFileGoogleDrive(lstRowExcel.get(11)); // Imagen logo
      File fileImageFacade = "".equals(lstRowExcel.get(12)) ? null : googleService.getFileGoogleDrive(lstRowExcel.get(12)); // Imagen fachada

      Store storeBD = storeService.getIdStoreByStoreNumber(lstRowExcel.get(0).trim().toUpperCase());

      Store store = new Store(
          (null == storeBD) ? null : storeBD.getIdStore(),
          "".equals(lstRowExcel.get(8)) ? null : getIdCategoryByName(lstRowExcel.get(8).toLowerCase()), // Id categoria
          Constants.ID_STATE_ACTIVE,
          "".equals(lstRowExcel.get(1)) ? null : lstRowExcel.get(1), // Nombre local
          "".equals(lstRowExcel.get(9)) ? null : lstRowExcel.get(9), // Descripción
          "".equals(lstRowExcel.get(0)) ? null : lstRowExcel.get(0).toUpperCase(), // Numero local
          "".equals(lstRowExcel.get(10)) ? null : lstRowExcel.get(10), // Ubicacion
          "".equals(lstRowExcel.get(3)) ? null : GenericFunctions.castNumericCell(lstRowExcel.get(3)), // Telefono
          (null != fileImage) ? "https://drive.google.com/uc?id=" + fileImage.getId() : Constants.URL_IMAGE_DEFAULT, // Url imagen logo
          (null != fileImageFacade) ? "https://drive.google.com/uc?id=" + fileImageFacade.getId() : Constants.URL_IMAGE_DEFAULT, // Url imagen fachada
          "".equals(lstRowExcel.get(7)) ? null : lstRowExcel.get(7), // Web
          "".equals(lstRowExcel.get(5)) ? null : lstRowExcel.get(5), // Instagram
          "".equals(lstRowExcel.get(4)) ? null : lstRowExcel.get(4), // Facebook
          "".equals(lstRowExcel.get(2)) ? null : GenericFunctions.castNumericCell(lstRowExcel.get(2)), // Whatsapp
          "".equals(lstRowExcel.get(6)) ? null : lstRowExcel.get(6) // Twitter
      );

      lstStores.add(store);
    }

    createStore(lstStores);

    return "Los locales fueron cargados correctamente";
  }


  /**
   * Método que permite obtener el id de la categoria según el nombre
   * @param categoryName Nombre de categoría
   * @return Id categoría
   */
  private Long getIdCategoryByName(String categoryName) 
  {
    Long idCategory = null;

    switch (categoryName) 
    {
      case "tiendas":
        idCategory = Constants.ID_CATEGORY_STORE;
        break;

      case "bancos":
        idCategory = Constants.ID_CATEGORY_BANK;
        break;

      case "entretenimiento":
        idCategory = Constants.ID_CATEGORY_ENTERTAINMENT;
        break;

      case "comidas":
        idCategory = Constants.ID_CATEGORY_FOOD;
        break;
                
      default:
        idCategory = null;
        break;
    }

    return idCategory;
  }


  /**
   * Método que permite crear locales
   * @param lstStores Lista de locales a crear
   * @throws Exception
   */
  public void createStore(List<Store> lstStores) throws Exception 
  {
    for(Store store : lstStores)
    {
      if(null != store.getIdCategory())
      {
        storeService.createStore(store);
      }
      else
      {
        System.out.println("Tienda: " + store.getName());
      }
    }
  }
}
