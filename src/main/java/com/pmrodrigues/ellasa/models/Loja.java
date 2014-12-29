package com.pmrodrigues.ellasa.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "areas_vendas" , joinColumns = @JoinColumn(name = "produto_id") ,
               inverseJoinColumns = @JoinColumn(name="secao_id"))
    private Set<Secao> secoes = new HashSet<>();

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
