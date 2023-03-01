package com.ihc.apirest.controllers;


import com.ihc.apirest.usecase.ProcessTicket;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/tickets")
@CrossOrigin("*")
public class TicketController 
{

  @Autowired
  ProcessTicket processTicket;
  

  /**
   * Método que permite obtener el historial de boletas de la última campaña según el cliente
   * @param headerAuthorization Contiene el token
   * @return Listado de facturas y cantidad de boletas acumuladas
   */
  @GetMapping(value = "/invoices/history")
  public ResponseEntity<Map<String, Object>> getLastTicketsByCampaign(@RequestHeader("Authorization") String headerAuthorization) 
  {
    Map<String, Object> mapResponse = processTicket.getLastTicketsByCampaign(headerAuthorization);

    return new ResponseEntity<Map<String, Object>>(mapResponse, HttpStatus.OK);
  }
}
