package com.ihc.apirest.models;


import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
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
