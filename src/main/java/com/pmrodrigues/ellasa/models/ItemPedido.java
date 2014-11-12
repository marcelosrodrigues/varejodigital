package com.pmrodrigues.ellasa.models;

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

/**
 * Created by Marceloo on 13/10/2014.
 */
@Entity
@Table(schema = "allinshopp" , name = "ps_order_detail")
public class ItemPedido implements Serializable{

    private static final java.math.BigDecimal CEM = new BigDecimal("100");

    private static final Logger logging = Logger.getLogger(ItemPedido.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_order_detail")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "product_attribute_id")
    private Atributo atributo;

    @Column(name = "product_quantity")
    private Long quantidade;

    @Column(name = "product_price")
    private BigDecimal preco;

    @Column(name = "total_price_tax_incl")
    private BigDecimal valor;

    @Column(name = "unit_price_tax_incl")
    private BigDecimal valorUnitario;

    @Column(name = "product_name")
    private String nome;

    @Column(name = "tax_name")
    private String taxName = "ELLASA";

    @ElementCollection
    @CollectionTable(name = "ps_order_detail_tax",
            schema = "allinshopp" ,
            joinColumns = @JoinColumn(name = "id_order_detail") )
    private Set<Comissao> comissao = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_shop")
    private Loja loja;

    @Column(name="product_weight")
    private BigDecimal peso;

    public ItemPedido(final Produto produto, final Long quantidade) {
        this();
        this.produto = produto;
        this.nome = produto.getNome();
        this.loja = produto.getLoja();
        this.preco = produto.getPreco();
        this.quantidade = quantidade;
        this.peso = produto.getPeso();
        this.valor = this.preco.multiply(new BigDecimal(quantidade));
        this.valorUnitario = this.preco;
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

        this.comissao.add(new Comissao(taxa,this.preco.multiply(taxa.getValor().divide(CEM)),this.quantidade));

    }


}
