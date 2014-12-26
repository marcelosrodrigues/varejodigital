package com.pmrodrigues.ellasa.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "atributo_produto")
public class Atributo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String descricao;
	
}
