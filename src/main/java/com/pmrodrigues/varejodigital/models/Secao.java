package com.pmrodrigues.varejodigital.models;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "secao")
@NamedQueries({@NamedQuery(name = "Secao.All", query = "SELECT s FROM Secao s left join fetch s.pai left join fetch s.subsecoes WHERE s.pai is null ORDER BY s.id")})
@DynamicUpdate(true)
@XmlType(name = "DepartamentoType" , namespace = "http://schema.varejodigital.projetandoo/1.0/")
@XmlAccessorType(XmlAccessType.FIELD)
public class Secao implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlTransient
    @OneToMany
    @JoinColumn(name = "pai_id")
    @OrderBy("id")
    private final Set<Secao> subsecoes = new HashSet<>();

    @XmlTransient
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "areas_vendas", joinColumns = @JoinColumn(name = "secao_id"),
            inverseJoinColumns = @JoinColumn(name = "loja_id"))
    private final Set<Loja> lojas = new HashSet<>(); //NOPMD

    @XmlElement(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @XmlElement(name = "nome")
    @Column(name = "secao")
    private String nome;

    @XmlTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pai_id")
    private Secao pai;

    @XmlTransient
    @Column(name = "icone")
    private String icone;

    public Set<Secao> getSubsecoes() {
        return subsecoes;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Secao) {
            final Secao other = (Secao) obj;
            return other.id == this.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (this.id == null) {
            return 0;
        } else {
            return this.id.hashCode();
        }
    }

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

    public Secao getPai() {
        return pai;
    }

    public void setPai(final Secao pai) {
        this.pai = pai;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(final String icone) {
        this.icone = icone;
    }
}
