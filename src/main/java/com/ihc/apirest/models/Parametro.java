package com.ihc.apirest.models;

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
@Table(name= "parametro", schema="configuracion")
public class Parametro
{
  @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idParametro;
	private String parametro;
	private String valor;
}