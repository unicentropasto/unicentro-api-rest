package com.ihc.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihc.apirest.repository.TicketRepository;



@Service
public class TicketService 
{

  @Autowired
  private TicketRepository ticketRepository;


  /**
   * Método que permite otener el historial de boletas de la última campaña según el cliente
   * @param idCustomer Id del cliente
   * @return Listado de boletas
   */
  public List<Object[]> getLastTickets(Long idCustomer) throws Exception 
  {
    return ticketRepository.getLastTickets(idCustomer);
  }


  /**
   * Método que permite otener la cantidad de boletas de la última campaña según el cliente
   * @param idCustomer Id del cliente
   * @return Cantidad de boletas y nombre de la última campaña
   */
  public List<Object[]> getQuantityTicketsByCampaign(Long idCustomer) throws Exception 
  {
    return ticketRepository.getQuantityTicketsByCampaign(idCustomer);
  }
}
