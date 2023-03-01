package com.ihc.apirest.models;


import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HistoryInvoiceByCampaignDTO implements Serializable
{

  private static final long serialVersionUID = 1L;
	

	private String campaignName;
	private Integer quantityTickets;
  private List<HistoryInvoiceDTO> lstHistoricalInvoices;
}





