package com.pmrodrigues.ellasa.models;

import com.pmrodrigues.ellasa.enumarations.StatusPagamento;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Marceloo on 13/10/2014.
 */
@Entity
@Table(schema = "allinshopp" , name = "ps_orders")
public class Pedido implements Serializable{

    @Transient
    private OrdemPagamento dadosPagamento;

    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false , cascade = CascadeType.ALL)
    @JoinColumn(name = "id_customer" , referencedColumnName = "id_customer")
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_address_delivery" , referencedColumnName = "id_address")
    private EnderecoCliente enderecoEntrega;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "id_order", referencedColumnName = "id_order" ,nullable = false)
    private Collection<ItemPedido> itens = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_shop" , referencedColumnName = "id_shop")
    private Loja loja;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_address_invoice" , referencedColumnName = "id_address")
    private EnderecoCliente enderecoPedido;

    @Column(name = "reference")
    private String codigoTransacao;

    @Column(name="current_state")
    @Enumerated(EnumType.ORDINAL)
    private StatusPagamento status = StatusPagamento.EM_ABERTO;

    @Column(name="id_carrier")
    private Long transportadora = 1L;

    @Column(name="id_lang")
    private Long idioma = 1L;

    @Column(name="id_cart")
    private Long carrinho = 0L;

    @Column(name="id_currency")
    private Long moeda = 1L;

    @Column(name="payment")
    private String pagamento = "";

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "invoice_date")
    private Date dataCompra = DateTime.now().toDate();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "delivery_date")
    private Date dataEntrega = DateTime.now().toDate();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_add")
    private Date dataCriacaco;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_upd")
    private Date dataAlteracao;

    @Column(name = "total_paid" )
    private BigDecimal valorPedido = BigDecimal.ZERO;

    @Column(name = "total_paid_tax_incl")
    private BigDecimal valorBruto = BigDecimal.ZERO;

    @Column(name = "total_paid_tax_excl")
    private BigDecimal valorLiquido = BigDecimal.ZERO;

    @Column(name = "total_paid_real" )
    private BigDecimal totalPedido =  BigDecimal.ZERO;

    @OneToOne(cascade = CascadeType.ALL , mappedBy = "pedido")
    @JoinTable(name = "vendas" , joinColumns =  {@JoinColumn(name = "pedido_id") })
    private Venda venda;

    @PrePersist
    public void onInsert() {
        dataCriacaco = DateTime.now().toDate();
        dataAlteracao = DateTime.now().toDate();

        calcularVenda();

    }

    protected void calcularVenda() {

        valorPedido = BigDecimal.ZERO;
        valorBruto = BigDecimal.ZERO;
        totalPedido = BigDecimal.ZERO;
        valorLiquido = BigDecimal.ZERO;

        for( final ItemPedido item : this.itens ){
            valorPedido = valorPedido.add(item.getValor());
            valorBruto = valorBruto.add(item.getValor());
            totalPedido = totalPedido.add(item.getValor());
            valorLiquido = valorLiquido.add(item.getValorLiquido());
        }
    }

    @PreUpdate
    public void onUpdate() {
        dataAlteracao = DateTime.now().toDate();
        calcularVenda();
    }

    public OrdemPagamento getDadosPagamento() {
        return dadosPagamento;
    }

    public void setDadosPagamento(final OrdemPagamentoCartaoCredito dadosPagamento) {
        this.dadosPagamento = dadosPagamento;
    }

    public void setCliente(final Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setEnderecoEntrega(final EnderecoCliente enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
        this.enderecoPedido = enderecoEntrega;
    }

    public EnderecoCliente getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public Collection<ItemPedido> getItens() {
        return itens;
    }

    public void setLoja(final Loja loja) {
        this.loja = loja;
    }

    public void setCodigoTransacao(final String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
    }

    public void calcula(final List<Taxa> taxas) {

        for(ItemPedido item : this.itens ){
            for(Taxa taxa : taxas) {
                item.geraComissao(taxa);
            }
        }

    }

    public void adicionar(final Produto produto) {
        this.itens.add(new ItemPedido(produto,1L));
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void associar(final Venda venda) {
        this.venda = venda;
        this.venda.associar(this);
    }
}