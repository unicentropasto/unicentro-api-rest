package com.ihc.apirest.usecase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ihc.apirest.models.HistoryInvoiceByCampaignDTO;
import com.ihc.apirest.models.HistoryInvoiceDTO;
import com.ihc.apirest.service.TicketService;
import com.ihc.apirest.utilities.Constants;


@Component
public class ProcessTicket
{
  @Autowired
  TicketService ticketService;

  @Autowired
  ProcessToken processToken;

  private static final int POISTION_URL_STORE_LOGO = 0;
  private static final int POISTION_STORE = 1;
  private static final int POISTION_INVOICE_VALUE = 2;

  private static final int POISTION_CAMPAIGN_NAME = 0;
  private static final int POISTION_QUANTITY_TICKETS = 1;


  /**
   * Método que permite otener el historial de boletas de la última campaña según el cliente
   * @param headerAuthorization Contiene el token
   * @return Listado de boletas
   */
  public  Map<String, Object> getLastTicketsByCampaign(String headerAuthorization)
  {
    Map<String, Object> mapResponse = new HashMap<>();

    try
    {
      // Se obtiene el parametro idCustomer de la propiedad claims del token
      Long idCustomer = processToken.getIdCustomerFromToken(headerAuthorization);

      List<HistoryInvoiceDTO> lstHistoryInvoices = new ArrayList<HistoryInvoiceDTO>();

      // Se construye un historial de facturas a partir de las boletas y la compra del cliente
      List<Object[]> lstLastTickets = ticketService.getLastTickets(idCustomer);

      // Construyento DTO para el historial de facturas
      for (Object[] row : lstLastTickets) 
      {
        lstHistoryInvoices.add(new HistoryInvoiceDTO(row[POISTION_URL_STORE_LOGO].toString(), row[POISTION_STORE].toString(), Integer.parseInt(row[POISTION_INVOICE_VALUE].toString())));
      }

      // Se obtiene una lista por temas de que no devuelvo ningún entity, dentro de la lista viene un solo registro simpre
      // que contiene el nombre de la última campaña y la cantidad de boletas acumuladas
      List<Object[]> lstQuantityTicketsCampaign = ticketService.getQuantityTicketsByCampaign(idCustomer);

      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_EMPTY_DATA_CODE);
      
      if(!lstQuantityTicketsCampaign.isEmpty())
      {
        HistoryInvoiceByCampaignDTO historyInvoiceByCampaignDTO = new HistoryInvoiceByCampaignDTO();
  
        // Se obtiene el nombre de la última campaña del primer registro de la lista
        historyInvoiceByCampaignDTO.setCampaignName(lstQuantityTicketsCampaign.get(0)[POISTION_CAMPAIGN_NAME].toString());
        
        // Se obtiene la cantidad de boletas acumuladas del primer registro de la lista
        historyInvoiceByCampaignDTO.setQuantityTickets(Integer.parseInt(lstQuantityTicketsCampaign.get(0)[POISTION_QUANTITY_TICKETS].toString()));
        
        historyInvoiceByCampaignDTO.setLstHistoricalInvoices(lstHistoryInvoices);
  
        mapResponse.put(Constants.RESPONSE_DATA, historyInvoiceByCampaignDTO);
        mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_OK_CODE);
      }

      return mapResponse;
    }
    catch (Exception e) 
    {
      mapResponse.put(Constants.RESPONSE_CODE, Constants.RESPONSE_ERROR_CODE);
      
      return mapResponse;
    }
  }
}
