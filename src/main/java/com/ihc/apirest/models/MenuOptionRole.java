package com.ihc.apirest.models;


import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(schema="appmall", name = "menu_options_role")
public class MenuOptionRole implements Serializable
{

  private static final long serialVersionUID = 1L;
	
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_menu_option_role")
	private Long idMenuOptionRole;

	@Basic(optional = false)
	@Column(name = "id_role")
	private Long idRole;
	
	@JoinColumn(name = "idMenuOption")
	@ManyToOne(optional = false)
	private MenuOption menuOption;
}
