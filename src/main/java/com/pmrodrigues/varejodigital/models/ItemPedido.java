package com.pmrodrigues.varejodigital.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Marceloo on 13/10/2014.
 */
@Entity
@Table(name = "item_pedido")
@XStreamAlias("item")
public class ItemPedido implements Serializable {

    private static final BigDecimal CEM = new BigDecimal("100");

    private static final Logger logging = Logger.getLogger(ItemPedido.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //NOPMD

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "atributo_id")
    private Atributo atributo;

    @Column(name = "quantidade")
    private Long quantidade;

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
