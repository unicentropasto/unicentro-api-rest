package com.ihc.apirest.models;


import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(schema="sisbol", name = "barrio")
public class VWNeighborhood 
{

  @Id
	@Column(name = "id_barrio")
	private Long idNeighborhood;
  
  @Column(name = "nombre_bar")
	private String neighborhood;
}
