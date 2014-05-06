package com.pmrodrigues.ellasa.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="ps_customer" , schema = "allinshopp")
@NamedQueries({@NamedQuery(name = "Cliente.All", query = "SELECT c FROM Cliente c inner join fetch c.endereco e inner join fetch e.estado ORDER BY c.id ASC")})
public class Cliente implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_customer")
	private Long id;
	
	@Column(name="firstname")
	private String primeiroNome;
	
	@Column(name="lastname")
	private String ultimoNome;
	
	@Column(name="email")
	private String email;
	
	@Temporal(TemporalType.DATE)
	@Column(name="birthday")
	private Date dataNascimento;
	
	@Column(name = "active")
	private final boolean active = true;
	
	@OneToOne(mappedBy="cliente")
	@JoinColumn(name="id_customer")
	private EnderecoCliente endereco;
	
}
