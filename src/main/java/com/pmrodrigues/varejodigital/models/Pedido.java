package com.pmrodrigues.varejodigital.models;

import com.pmrodrigues.varejodigital.Constante;
import com.pmrodrigues.varejodigital.enumarations.StatusPagamento;
import com.pmrodrigues.varejodigital.repositories.utils.FilterName;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.hibernate.annotations.*;
import org.joda.time.DateTime;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by Marceloo on 13/10/2014.
 */
@Entity
@Table
@XStreamAlias("pedido")
@FilterDefs({
        @FilterDef(name = FilterName.FILTRO_POR_VENDEDOR, parameters = @ParamDef(name = FilterName.FILTRO_POR_VENDEDOR, type = "long")),
        @FilterDef(name = FilterName.FILTRO_POR_LOJA, parameters = @ParamDef(name = FilterName.FILTRO_POR_LOJA, type = "long"))
})
@Filters({
        @Filter(name = FilterName.FILTRO_POR_VENDEDOR, condition = "vendedor_id = :vendedor"),
        @Filter(name = FilterName.FILTRO_POR_LOJA, condition = "exists ( select 1 from lojistas l where l.loja_id = loja_id and l.usuario_id = :loja)")
})
public class Pedido implements Serializable {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pedido_id", nullable = false)
    private final Collection<ItemPedido> itens = new HashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(optional = false, cascade = CascadeType.ALL, targetEntity = Cliente.class)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @ManyToOne(optional = false)
    @JoinColumn(name = "enderecoentrega_id")
    private EnderecoCliente enderecoEntrega;
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
    private Date dataCompra = Constante.DATA_INICIAL;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date dataEntrega = DateTime.now().toDate(); //NOPMD

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dataCriacaco")
    private Date dataCriacao; //NOPMD

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date dataAlteracao; //NOPMD

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "vendedor_id")
    private Usuario vendedor; //NOPMD

    @PrePersist
    public void onInsert() {
        dataCriacao = DateTime.now().toDate();
        dataAlteracao = DateTime.now().toDate();
        dataCompra = DateTime.now().toDate();
    }

    @PreUpdate
    public void onUpdate() {
        dataAlteracao = DateTime.now().toDate();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(final Cliente cliente) {
        this.cliente = cliente;
    }

    public EnderecoCliente getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(final EnderecoCliente enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public Collection<ItemPedido> getItens() {
        return itens;
    }

    public BigDecimal getTotal() {
        BigDecimal valor = BigDecimal.ZERO;
        for (final ItemPedido item : itens) {
            valor = valor.add(item.getValor());
        }

        return valor;
    }

    public void adicionar(final Produto produto) {
        this.itens.add(new ItemPedido(produto, 1L));
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

    public void setLoja(final Loja loja) {
        this.loja = loja;
    }

    public String getCodigoTransacao() {
        return codigoTransacao;
    }

    public void setCodigoTransacao(final String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(final StatusPagamento status) {
        this.status = status;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(final Date dataCompra) {
        this.dataCompra = dataCompra;
    }
}
