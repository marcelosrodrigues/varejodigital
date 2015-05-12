package com.pmrodrigues.varejodigital.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@NamedQueries({@NamedQuery(name = "TipoFranquia.All", query = "FROM TipoFranquia ORDER BY valor ASC")})
public class TipoFranquia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

}
