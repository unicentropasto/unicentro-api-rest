package com.ihc.apirest.models;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(schema="sisbol", name = "boleta")
public class Ticket implements Serializable
{

  private static final long serialVersionUID = 1L;
	
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codi_bol")
	private Long idTicket;

  @Basic(optional = false)
	@Column(name = "codi_cli")
	private Long idCustomer;

	@Basic(optional = false)
	@Column(name = "id_camp")
	private Long idCamp;

	@Basic(optional = false)
	@Column(name = "esta_bol")
	private String state;

  @Basic(optional = true)
	@Column(name = "impr_bol")
	private String print;

  @Basic(optional = true)
	@Column(name = "anul_bol")
	private String canceled;

  @Basic(optional = true)
	@Column(name = "fech_bol")
	private Date date;

  @Basic(optional = true)
	@Column(name = "usua_bol")
	private Integer usuaBol;

  @Basic(optional = true)
	@Column(name = "placavehi_bol")
	private String placavehiBol;
}