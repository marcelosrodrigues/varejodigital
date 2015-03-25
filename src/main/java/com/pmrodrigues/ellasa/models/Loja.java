package com.pmrodrigues.ellasa.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "areas_vendas", joinColumns = @JoinColumn(name = "loja_id"),
            inverseJoinColumns = @JoinColumn(name = "secao_id"))
    private final List<Secao> secoes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public List<Secao> getSecoes() {
        return secoes;
    }

    public void setSecoes(final List<Secao> secoes) {
        this.secoes.clear();
        this.secoes.addAll(secoes);
    }
}
