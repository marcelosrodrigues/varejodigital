package com.pmrodrigues.ellasa.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "secao")
@NamedQueries({@NamedQuery(name = "Secao.All", query = "SELECT s FROM Secao s left join fetch s.pai left join fetch s.subsecoes WHERE s.pai is null ORDER BY s.id")})
public class Secao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "secao")
	private String nome;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pai_id")
	private Secao pai;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Secao getPai() {
        return pai;
    }

    public void setPai(Secao pai) {
        this.pai = pai;
    }

    @OneToMany
	@JoinColumn(name = "pai_id")
    @OrderBy("id")
    private final Set<Secao> subsecoes = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "areas_vendas", joinColumns = @JoinColumn(name = "secao_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private Set<Loja> lojas = new HashSet<>();

    public Set<Secao> getSubsecoes() {
        return subsecoes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Secao) {
            Secao other = (Secao) obj;
            return other.id == this.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
