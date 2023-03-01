package com.ihc.apirest.models;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
	@OrderBy("name")
	private List<Store> lstStores;
}
