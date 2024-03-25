package com.ihc.apirest.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(schema="sisbol", name = "barrio")
public class Neighborhood 
{

  @Id
	@Column(name = "id_barrio")
	private Long idNeighborhood;
  
  @Column(name = "nombre_bar")
	private String neighborhood;
}
