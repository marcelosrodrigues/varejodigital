package com.pmrodrigues.ellasa.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ps_address" , schema = "allinshopp")
public class EnderecoCliente {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_address")
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="id_customer")
	private Cliente cliente;
	
	@Column(name = "alias")
	private final String alias = "ELLASA";
	
	@Column(name="firstname")
	private String primeiroNome;
	
	@Column(name="lastname")
	private String ultimoNome;
	
	@Column(name="address1")
	private String logradouro;
	
	@Column(name="address2")
	private String bairro;
	
	@Column(name="postcode")
	private String cep;
	
	@Column(name="city")
	private String cidade;
	
	@Column(name="phone")
	private String telefone;
	
	@Column(name="phone_mobile")
	private String celular;
	
	@Column(name="active")
	private boolean active;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="id_state")
	private Estado estado;

}
