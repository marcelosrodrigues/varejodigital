package com.pmrodrigues.ellasa.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

/**
 * Created by Marceloo on 13/10/2014.
 */
@Entity
@Table(name="item_pedido")
@XStreamAlias("item")
public class ItemPedido implements Serializable{

    private static final java.math.BigDecimal CEM = new BigDecimal("100");

    private static final Logger logging = Logger.getLogger(ItemPedido.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "atributo_id")
    private Atributo atributo;

    @Column(name = "quantidade")
    private Long quantidade;

    @ElementCollection
    @CollectionTable(name = "comissao", joinColumns = @JoinColumn(name = "item_id") )
    private Set<Comissao> comissoes = new HashSet<>();

    @Column
    private BigDecimal preco;


    public ItemPedido(final Produto produto, final Long quantidade) {
        this();
        this.produto = produto;
        this.quantidade = quantidade;
        this.preco = produto.getPreco();
    }

    public ItemPedido() {}

    public Produto getProduto() {
        return produto;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void geraComissao(final Taxa taxa) {

        if( logging.isDebugEnabled() ){

            logging.debug(format("Calculando as comissões para o produto %s",this.produto));
            logging.debug(format("comissao %s valor %s",taxa.getNome() , this.preco.divide(taxa.getValor().divide(CEM),BigDecimal.ROUND_UP).subtract(preco),this.quantidade));

        }

        this.comissoes.add(new Comissao(taxa, this.preco.multiply(taxa.getValor().divide(CEM)), this.quantidade));

    }


    public BigDecimal getValor() {
        return this.preco.multiply(new BigDecimal(quantidade));
    }

    public void setProduto(final Produto produto) {
        this.produto = produto;
        this.preco = produto.getPreco();
    }
}
