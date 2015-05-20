package com.pmrodrigues.varejodigital.models;

import com.pmrodrigues.varejodigital.repositories.utils.FilterName;
import com.pmrodrigues.varejodigital.webservice.adapters.BigDecimalTypeAdapter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "produto")
@XStreamAlias("produto")
@NamedQueries({@NamedQuery(name = "Produto.All", query = "SELECT p FROM Produto p inner join fetch p.secao s inner join fetch p.loja left join fetch p.imagens i left join fetch p.atributos a ORDER BY s.id")})
@FilterDefs({
        @FilterDef(name = FilterName.FILTRO_POR_LOJA, parameters = @ParamDef(name = FilterName.FILTRO_POR_LOJA, type = "long"))
})
@Filters({
        @Filter(name = FilterName.FILTRO_POR_LOJA, condition = "exists ( select 1 from lojistas l where l.loja_id = loja_id and l.usuario_id = :loja)")
})
@XmlType(name = "ProdutoType" , namespace = "http://schema.varejodigital.projetandoo/1.0/")
@XmlAccessorType(XmlAccessType.FIELD)
@DynamicUpdate
@DynamicInsert
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlTransient
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "produto_id", nullable = false)
    @OrderBy("id asc")
    private final Set<Imagem> imagens = new HashSet<>();

    @XmlTransient
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "produto_id", nullable = false)
    @OrderBy("id asc")
    private final Set<Atributo> atributos = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "loja_id")
    @XmlElement(name = "loja" , required = true)
    private Loja loja;

    @XmlElement(name = "departamanento")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "secao_id", referencedColumnName = "id")
    private Secao secao;

    @XmlElement(name = "nome" , required = true)
    @Column(name = "nome")
    private String nome;

    @XmlTransient
    @Column(name = "descricao")
    private String descricao;

    @XmlTransient
    @Column(name = "descricao_curta")
    private String descricaoBreve;

    @XmlJavaTypeAdapter(value = BigDecimalTypeAdapter.class, type = BigDecimal.class)
    @XmlElement(name = "preco" , required = true)
    @Column(name = "preco")
    @XmlSchemaType(name = "decimal")
    private BigDecimal preco;

    @XmlJavaTypeAdapter(value = BigDecimalTypeAdapter.class, type = BigDecimal.class)
    @XmlElement(name = "custo" , required = true)
    @Column(name = "preco_custo")
    @XmlSchemaType(name = "decimal")
    private BigDecimal custo;

    @XmlJavaTypeAdapter(value = BigDecimalTypeAdapter.class, type = BigDecimal.class)
    @XmlElement(name = "peso" , required = true)
    @Column(name = "peso")
    @XmlSchemaType(name = "decimal")
    private BigDecimal peso;

    @XmlElement(name = "estoque" , required = true)
    @Embedded
    private Estoque estoque;

    @XmlElement(name = "codigoBarra" , required = true)
    @Column(name="codigo_barra")
    private String codigoBarra;

    @XmlElement(name = "codigoInterno" , required = true)
    @Column(name="codigo_interno")
    private Long codigoInterno;

    @XmlElement(name = "codigoExterno" , required = true)
    @Column(name="codigo_externo")
    private Long codigoExterno;

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(final Loja loja) {
        this.loja = loja;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(final BigDecimal preco) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(final BigDecimal peso) {
        this.peso = peso;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void adicionar(final Imagem imagem) {
        this.imagens.add(imagem);
    }

    public Set<Imagem> getImagens() {
        return imagens;
    }

    public Secao getSecao() {
        return secao;
    }

    public void setSecao(final Secao secao) {
        this.secao = secao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    public String getDescricaoBreve() {
        return descricaoBreve;
    }

    public void setDescricaoBreve(final String descricaoBreve) {
        this.descricaoBreve = descricaoBreve;
    }

    public Set<Atributo> getAtributos() {
        return atributos;
    }

    public void adicionar(final Atributo atributo) {
        this.atributos.add(atributo);
    }

    public Long getCodigoExterno() {
        return codigoExterno;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public BigDecimal getCusto() {
        return custo;
    }

    public String getCodigoBarras() {
        return codigoBarra;
    }

    public void setEstoque(final Estoque estoque) {
        this.estoque = estoque;
    }

    public void setCusto(final BigDecimal custo) {
        this.custo = custo;
    }

    public void setCodigoBarras(final String codigoBarras) {
        this.codigoBarra = codigoBarras;
    }

    public void setCodigoExterno(final Long codigoExterno) {
        this.codigoExterno = codigoExterno;
    }

    public void setCodigoInterno(final Long codigoInterno) {
        this.codigoInterno = codigoInterno;
    }
}
