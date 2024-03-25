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
@Table(schema="sisbol", name = "tipo")
public class IdentificationType 
{

  @Id
	@Column(name = "codi_tip")
	private Long idIdentificationType;
  
  @Column(name = "valo_tip")
	private String name;

  @Column(name = "desc_tip")
	private String description;

	@Column(name = "codi_gru")
	private String groupCode;
}
