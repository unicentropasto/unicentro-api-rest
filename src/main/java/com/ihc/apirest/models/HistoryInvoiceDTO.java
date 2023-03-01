package com.ihc.apirest.models;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HistoryInvoiceDTO implements Serializable
{

  private static final long serialVersionUID = 1L;
	

	private String urlStoreLogo;
	private String store;
	private Integer invoiceValue;
}