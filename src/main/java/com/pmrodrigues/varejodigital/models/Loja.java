package com.pmrodrigues.varejodigital.models;

import com.pmrodrigues.varejodigital.repositories.utils.FilterName;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Marceloo on 13/10/2014.
 */
@Entity
@Table
@NamedQueries({@NamedQuery(name = "Loja.All", query = "SELECT c FROM Loja c ORDER BY c.nome ASC")})
@FilterDefs({
        @FilterDef(name = FilterName.FILTRO_POR_LOJA, parameters = @ParamDef(name = FilterName.FILTRO_POR_LOJA, type = "long"))
})
@Filters({
        @Filter(name = FilterName.FILTRO_POR_LOJA, condition = "exists ( select 1 from lojistas l where l.loja_id = id and l.usuario_id = :loja)")
})
public class Loja implements Serializable {

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "areas_vendas", joinColumns = @JoinColumn(name = "loja_id"),
            inverseJoinColumns = @JoinColumn(name = "secao_id"))
    private final List<Secao> secoes = new ArrayList<>();
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

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public Collection<Secao> getSecoes() {
        return secoes;
    }

    public void setSecoes(final Collection<Secao> secoes) {
        this.secoes.clear();
        this.secoes.addAll(secoes);
    }
}
