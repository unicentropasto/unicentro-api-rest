package com.ihc.apirest.usecase;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ihc.apirest.models.Promotion;
import com.ihc.apirest.models.Store;
import com.ihc.apirest.service.PromotionService;
import com.ihc.apirest.service.StoreService;
import com.ihc.apirest.service.CloudinaryService;
import com.ihc.apirest.service.GoogleService;
import com.ihc.apirest.utilities.Constants;
import com.ihc.apirest.utilities.GenericFunctions;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

// import com.google.api.services.drive.model.File;


@Component
public class ProcessPromotion 
{
  @Autowired
  GoogleService googleService;

  @Autowired
  CloudinaryService cloudinaryService;

  @Autowired
  PromotionService promotionService;

  @Autowired
  StoreService storeService;


  /**
   * Método que permite obtener todas las promociones
   * @return Listado de promociones
   */
  public Map<String, Object> getAllPromotions()
  {
    Map<String, Object> mapResponse = new HashMap<>();

    try
    {
      List<Promotion> lstPromotions = promotionService.getAllPromotions();
      
      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_OK_CODE);
      mapResponse.put(Constants.RESPONSE_DATA, lstPromotions);

      return mapResponse;
    }
    catch (Exception e) 
    {
      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_ERROR_CODE);
      return mapResponse;
    }
  }


  /**
   * Método que permite cargar las promociones desde el repositorio google drive
   * @return Lista de promociones
   * @throws IOException
   * @throws GeneralSecurityException
   * @throws Exception
   */
  public String loadPromotions() throws IOException, GeneralSecurityException, Exception
  {
    String msj = null;

    List<List<String>> lstDataExcel =  googleService.loadDataFileGoogleDrive("Promociones.xlsx", "Promociones", 6);

    List<Promotion> lstPromotions = new ArrayList<>();

    for (List<String> lstRowExcel : lstDataExcel) 
    {
      // La columna 10 del excel de promociones representa el nombre de la imagen que se debe buscar en el drive de google para obtener su ID
      // File fileImage = "".equals(lstRowExcel.get(5)) ? null : googleService.getFileGoogleDrive(lstRowExcel.get(5)); // Imagen promoción
      String fileImage = "".equals(lstRowExcel.get(5)) ? null : cloudinaryService.loadImage(lstRowExcel.get(5)); // Imagen logo

      Store store = storeService.getIdStoreByStoreNumber(lstRowExcel.get(0).trim());

      // En caso de que se detecte que hay un número de local invalido se debe rechazar todo el archivo
      if(null != store)
      {
        Promotion promotion = new Promotion(
            null,
            "".equals(lstRowExcel.get(0)) ? null : store, // Id local
            Constants.ID_STATE_ACTIVE,
            "".equals(lstRowExcel.get(1)) ? null : lstRowExcel.get(1), // Nombre promoción
            "".equals(lstRowExcel.get(2)) ? null : lstRowExcel.get(2), // Descripción
            "".equals(lstRowExcel.get(3)) ? null : GenericFunctions.castNumericCell(lstRowExcel.get(3)), // Fecha inicio
            "".equals(lstRowExcel.get(4)) ? null : GenericFunctions.castNumericCell(lstRowExcel.get(4)), // Fecha fin
            // (null != fileImage) ? "https://drive.google.com/uc?id=" + fileImage.getId() : Constants.URL_IMAGE_DEFAULT, // Url imagen promoción
            (null != fileImage) ? fileImage : Constants.URL_IMAGE_DEFAULT, // Url imagen promoción
            new Date()
        );
  
        lstPromotions.add(promotion);
      }
      else
      {
        msj = "El número de local [ " + lstRowExcel.get(0) + " ] es inválido, favor validar dicho número en sisbol.";
        break;
      }
    }

    // Se valida que no exita mensajes de error al crear el listado de promociones
    if(null == msj)
    {
      createPromotion(lstPromotions);
      return "Las promociones fueron cargadas correctamente";
    }

    return msj;
  }


  /**
   * Método que permite crear promociones
   * @param lstPromotions Lista de promociones a crear
   * @throws Exception
   */
  public void createPromotion(List<Promotion> lstPromotions) throws Exception 
  {
    promotionService.deletePromotion();

    for(Promotion promotion : lstPromotions)
    {
      promotionService.createPromotion(promotion);
    }
  }
}
