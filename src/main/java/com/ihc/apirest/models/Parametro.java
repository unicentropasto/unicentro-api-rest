package com.ihc.apirest.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name= "parametro", schema="configuracion")
public class Parametro
{
  @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idParametro;
	private String parametro;
	private String valor;
}