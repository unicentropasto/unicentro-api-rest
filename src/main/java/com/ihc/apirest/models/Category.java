package com.ihc.apirest.models;


import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(schema="appmall", name = "categories")
public class Category implements Serializable
{

  private static final long serialVersionUID = 1L;
	
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_category")
	private Long idCategory;

  @Basic(optional = false)
	@Column(name = "id_state")
	private Long idState;

	@Basic(optional = false)
	@Column(name = "name")
	private String name;

  @Basic(optional = true)
	@Column(name = "background_color")
	private String backgroundColor;

  @Basic(optional = true)
	@Column(name = "type")
	private String type;

  @Basic(optional = true)
	@Column(name = "url_category_image")
	private String urlCategoryImage;

  @Basic(optional = true)
	@Column(name = "url_category_icon1")
	private String urlCategoryIcon1;

  @Basic(optional = true)
	@Column(name = "url_category_icon2")
	private String urlCategoryIcon2;

  @Basic(optional = true)
	@Column(name = "order_category")
	private Integer orderCategory;

  @OneToMany(mappedBy = "idCategory", cascade = CascadeType.ALL)
	private List<Store> lstStores;
}
