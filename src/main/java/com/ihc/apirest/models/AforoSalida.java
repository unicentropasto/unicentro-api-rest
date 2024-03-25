package com.ihc.apirest.models;

import java.util.Date;

import jakarta.persistence.Basic;
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
@Table(name= "aforo_salida", schema="hechos")
public class AforoSalida
{
	@Id
	@Basic(optional = false)
  @Column(name = "fecha_salida")
	private Date fechaSalida;

	@Basic(optional = true)
  @Column(name = "conteo")
	private Integer conteo;
}