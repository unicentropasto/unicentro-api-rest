package com.ihc.apirest.models;


import java.io.Serializable;
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
@Table(schema="appmall", name = "configuration_images")
public class ConfigurationImage implements Serializable
{

  private static final long serialVersionUID = 1L;
	
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_configuration")
	private Long idConfiguration;

	@Basic(optional = false)
	@Column(name = "description")
	private String description;

  @Basic(optional = false)
	@Column(name = "value")
	private String value;

  @Basic(optional = false)
	@Column(name = "type")
	private String type;
}
