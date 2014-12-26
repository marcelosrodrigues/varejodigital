package com.pmrodrigues.ellasa.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Marceloo on 13/10/2014.
 */
@Entity
@Table
@NamedQueries({@NamedQuery(name = "Loja.All", query = "SELECT c FROM Loja c ORDER BY c.nome ASC")})
public class Loja implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Nome da empresa parceira não pode ser vazio")
    @Column(name = "nome")
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
