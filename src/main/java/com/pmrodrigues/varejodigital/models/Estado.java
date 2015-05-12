package com.pmrodrigues.varejodigital.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table
@NamedQueries({@NamedQuery(name = "Estado.All", query = "FROM Estado ORDER BY uf ASC")})
public class Estado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(columnDefinition = "char(2)", nullable = false, unique = true)
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

    public void setId(final Long id) {
        this.id = id;
    }
}
