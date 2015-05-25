package com.pmrodrigues.varejodigital.models;

import com.pmrodrigues.varejodigital.webservice.adapters.BigDecimalTypeAdapter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.log4j.Logger;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Marceloo on 13/10/2014.
 */
@Entity
@Table(name = "item_pedido")
@XStreamAlias("item")
@XmlType(name = "ItemPedidoType" , namespace = "http://schema.varejodigital.projetandoo/1.0/")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemPedido implements Serializable {

    private static final BigDecimal CEM = new BigDecimal("100");

    private static final Logger logging = Logger.getLogger(ItemPedido.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Long id; //NOPMD

    @XmlElement(name = "produto")
    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @XmlTransient
    @ManyToOne
    @JoinColumn(name = "atributo_id")
    private Atributo atributo;

    @XmlElement(name = "quantidade")
    @Column(name = "quantidade")
    private Long quantidade;

    @XmlJavaTypeAdapter(value = BigDecimalTypeAdapter.class, type = BigDecimal.class)
    @XmlElement(name = "preco" , required = true)
    @XmlSchemaType(name = "decimal")
    @Column
    private BigDecimal preco;

    public ItemPedido(final Produto produto, final Long quantidade) {
        this();
        this.produto = produto;
        this.quantidade = quantidade;
        this.preco = produto.getPreco();
    }

    public ItemPedido() {
        //NOPMD
    }

    @PrePersist
    public void preInsert() {
        if (preco == null) {
            preco = produto.getPreco();
        }
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(final Produto produto) {
        this.produto = produto;
        this.preco = produto.getPreco();
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(final Long quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return this.preco.multiply(new BigDecimal(quantidade));
    }

    public Atributo getAtributo() {
        return atributo;
    }

    public BigDecimal getPreco() {
        return preco;
    }
}
