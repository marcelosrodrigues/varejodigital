package com.pmrodrigues.ellasa.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "atributo_produto")
public class Atributo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;

	@Column
	private String descricao;
	

}
