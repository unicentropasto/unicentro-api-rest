package com.ihc.apirest.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ihc.apirest.security.Rol;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(schema="sisbol", name = "cliente")
public class Customer implements UserDetails
{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codi_cli")
	private Long idCustomer;

	@Basic(optional = true)
	@Column(name = "tpid_cli")
	private String idIdentificationType;

	@Basic(optional = false)
	@Column(name = "nrod_cli")
	private String identificationDocument;

	@Basic(optional = false)
	@Column(name = "nomb_cli")
	private String firstName;
	
	@Basic(optional = false)
	@Column(name = "apel_cli")
	private String lastName;

	@Basic(optional = true)
	@Column(name = "dire_cli")
	private String address;

	@Basic(optional = true)
	@Column(name = "tele_cli")
	private String phone;

	@Basic(optional = true)
	@Column(name = "fnac_cli")
	private Date birthday;

	@Basic(optional = true)
	@Column(name = "sexo_cli")
	private String gender;

	@Basic(optional = true)
	@Column(name = "emai_cli")
	private String email;

	@Basic(optional = true)
	@Column(name = "id_barrio")
	private Long idNeighborhood;

	@Basic(optional = true)
	@Column(name = "customer_type")
	private String customerType;
	
	@Basic(optional = true)
	@Column(name = "password", updatable = false)
	private String password;

	@Basic(optional = true)
	@Column(name = "id_role")
	private Long idRole;

	@Basic(optional = true)
	@Column(name = "pet")
	private Boolean isPet;

	@Basic(optional = true)
	@Column(name = "terms_conditions")
	private Boolean isTermsConditions;


	
	/**************************************************************************************************************************
	 * Metodos derivados de la implementación "UserDetails" para el manejo de seguridad y autenticación del customer
	 * ************************************************************************************************************************/

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		List<GrantedAuthority> lstRoles = new ArrayList<>();
    lstRoles.add(new SimpleGrantedAuthority(Rol.ROLE_CUSTOMER.name()));
    
		return lstRoles;
	}


	// @JsonIgnore
	@Override
	public String getPassword() 
	{
		return password;
	}


	@JsonIgnore
	@Override
	public String getUsername() 
	{
		return email;
	}


	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() 
	{
		return true;
	}


	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() 
	{
		return true;
	}


	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() 
	{
		return true;
	}


	@JsonIgnore
	@Override
	public boolean isEnabled() 
	{
		return true;
	}
}