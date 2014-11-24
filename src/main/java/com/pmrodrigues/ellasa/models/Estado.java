package com.pmrodrigues.ellasa.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
@NamedQueries({@NamedQuery(name = "Estado.All", query = "FROM Estado ORDER BY uf ASC")})
public class Estado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Long id;
	
	@NotNull
	@Column(columnDefinition = "char(2)", nullable = false , unique = true)
	private String uf;

	@NotNull
	@Column(insertable = true, updatable = true, nullable = false)
	private String nome;

	public String getUf() {
		return uf;
	}

	public void setUf(final String uf) {
		this.uf = uf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}

    public Long getId() {
        return id;
    }
}
