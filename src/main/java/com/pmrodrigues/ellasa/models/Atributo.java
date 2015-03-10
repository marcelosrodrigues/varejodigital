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

    public Atributo(final String valor) {
        this();
        this.descricao = valor;
    }

    public Atributo() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Atributo) {
            Atributo other = (Atributo) obj;
            return this.descricao.equalsIgnoreCase(other.descricao);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.descricao.hashCode();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
