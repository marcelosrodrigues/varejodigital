package com.pmrodrigues.ellasa.models;

import com.pmrodrigues.ellasa.enumarations.StatusPagamento;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Marceloo on 13/10/2014.
 */
@Entity
@Table
public class Pedido implements Serializable{

    @Transient
    @XStreamAlias("pagamento")
    private OrdemPagamento dadosPagamento;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false , cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "enderecoentrega_id")
    private EnderecoCliente enderecoEntrega;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Collection<ItemPedido> itens = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "loja_id")
    private Loja loja;

    @Column
    private String codigoTransacao;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private StatusPagamento status = StatusPagamento.EM_ABERTO;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date dataCompra = DateTime.now().toDate();

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date dataEntrega = DateTime.now().toDate();

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date dataCriacaco;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date dataAlteracao;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name = "vendedor_id")
    private Usuario vendedor;

    @PrePersist
    public void onInsert() {
        dataCriacaco = DateTime.now().toDate();
        dataAlteracao = DateTime.now().toDate();
    }

    @PreUpdate
    public void onUpdate() {
        dataAlteracao = DateTime.now().toDate();
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

        for(final ItemPedido item : this.itens ){
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

    public void associar(final Usuario usuario) {
        this.vendedor = usuario;
    }

    public Loja getLoja() {
        return loja;
    }
}
