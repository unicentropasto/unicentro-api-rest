package com.ihc.apirest.models;


import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(schema="appmall", name = "stores")
public class Store implements Serializable
{

  private static final long serialVersionUID = 1L;
	
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_store")
	private Long idStore;
	
  @Basic(optional = false)
	@Column(name = "id_category")
	private Long idCategory;

  @Basic(optional = false)
	@Column(name = "id_state")
	private Long idState;

  @Basic(optional = false)
	@Column(name = "name")
	private String name;

  @Basic(optional = false)
	@Column(name = "description")
	private String description;

  @Basic(optional = false)
	@Column(name = "store_number")
	private String storeNumber;

  @Basic(optional = false)
	@Column(name = "store_location")
	private String storeLocation;

  @Basic(optional = true)
	@Column(name = "phone")
	private String phone;

	@Basic(optional = false)
	@Column(name = "url_store_logo")
	private String urlStoreLogo;

  @Basic(optional = false)
	@Column(name = "url_store_image")
	private String urlStoreImage;

  @Basic(optional = true)
	@Column(name = "website")
	private String website;

  @Basic(optional = true)
	@Column(name = "instagram")
	private String instagram;

  @Basic(optional = true)
	@Column(name = "facebook")
	private String facebook;

  @Basic(optional = true)
	@Column(name = "whatsapp")
	private String whatsapp;

	@Basic(optional = true)
	@Column(name = "twitter")
	private String twitter;
}
