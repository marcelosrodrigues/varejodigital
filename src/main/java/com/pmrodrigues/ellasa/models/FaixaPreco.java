package com.pmrodrigues.ellasa.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "faixa_preco")
@NamedQueries({@NamedQuery(name = "FaixaPreco.All", query = "SELECT f FROM FaixaPreco f inner join fetch f.origem inner join fetch f.destino ORDER BY f.origem.uf, f.destino.uf")})
public class FaixaPreco implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "uf_destino")
	private Estado destino;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "uf_origem")
	private Estado origem;
		
	@Column(nullable = false , name = "preco")
	private BigDecimal preco;
	

}
