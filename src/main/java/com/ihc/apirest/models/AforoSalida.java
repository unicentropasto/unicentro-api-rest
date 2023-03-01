package com.ihc.apirest.models;

import java.util.Date;

import javax.persistence.Basic;
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