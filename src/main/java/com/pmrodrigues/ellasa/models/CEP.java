package com.pmrodrigues.ellasa.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name= "cep")
@NamedQueries({@NamedQuery(name = "CEP.All", query = "SELECT c FROM CEP c inner join fetch c.estado inner join fetch c.faixas f inner join fetch f.destino ORDER BY c.estado.uf")})
public class CEP implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "estado_id")
	private Estado estado;
	
	@Column(nullable = false)
	private Long inicial;
	
	@Column(nullable = false , name = "final")
	private Long fim;
	
	@OneToMany
	@JoinColumn(name="cep_id" , referencedColumnName="id")
	private final Set<FaixaPreco> faixas = new HashSet<>();
}
