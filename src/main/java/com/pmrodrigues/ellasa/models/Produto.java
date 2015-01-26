package com.pmrodrigues.ellasa.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "produto")
@XStreamAlias("produto")
@NamedQueries({@NamedQuery(name = "Produto.All", query = "SELECT p FROM Produto p inner join fetch p.secao s inner join fetch p.loja left join fetch p.imagens i left join fetch p.atributos a ORDER BY s.id")})
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "loja_id")
    private Loja loja;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "secao_id", referencedColumnName = "id")
    private Secao secao;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "descricao_curta")
    private String descricaoBreve;

    @Column(name = "preco")
    private BigDecimal preco;

    @Column(name = "peso")
    private BigDecimal peso;

    @OneToMany
    @JoinColumn(name = "produto_id")
    private final Set<Imagem> imagens = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "produto_id")
    private final Set<Atributo> atributos = new HashSet<>();

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(final Loja loja) {
        this.loja = loja;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
