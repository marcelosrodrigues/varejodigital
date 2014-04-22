package com.pmrodrigues.ellasa.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "faixa_preco")
public class FaixaPreco implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "uf_destino")
	private Estado destino;
	
	@Column(nullable = false , name = "peso_inicial")
	private BigDecimal inicial;
	
	@Column(nullable = false , name = "peso_final")
	private BigDecimal termino;
	
	@Column(nullable = false , name = "preco")
	private BigDecimal preco;

}
