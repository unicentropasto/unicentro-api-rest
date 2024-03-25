package com.ihc.apirest.models;


import java.io.Serializable;
import java.util.Date;
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
@Table(schema="appmall", name = "menu_options")
public class MenuOption implements Serializable
{

  private static final long serialVersionUID = 1L;
	
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_menu_option")
	private Long idMenuOption;

  @Basic(optional = true)
	@Column(name = "id_menu_option_parent")
	private Long idMenuOptionParent;

  @Basic(optional = false)
	@Column(name = "id_state")
	private Long idState;

	@Basic(optional = false)
	@Column(name = "menu_option")
	private String menuOption;

  @Basic(optional = false)
	@Column(name = "description")
	private String description;
  
  @Basic(optional = false)
	@Column(name = "url_menu_option_image")
	private String urlMenuOptionImage;

  @Basic(optional = true)
	@Column(name = "redirect")
	private String redirect;

  @Basic(optional = true)
	@Column(name = "color")
	private String color;

  @Basic(optional = false)
	@Column(name = "level")
	private Integer level;

  @Basic(optional = false)
	@Column(name = "menu_option_order")
	private Integer menuOptionOrder;

  @Basic(optional = false)
	@Column(name = "creation_date", updatable = false)
	private Date creationDate;

  @Basic(optional = true)
	@Column(name = "modification_date")
	private Date modificationDate;
}
