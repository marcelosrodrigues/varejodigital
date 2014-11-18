package com.pmrodrigues.ellasa.models;

import com.sun.mail.imap.protocol.Item;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Marceloo on 14/10/2014.
 */
@Embeddable
public class Comissao implements Serializable{


    @ManyToOne(optional = false)
    @JoinColumn( name = "id_tax")
    private Taxa taxa;

    @Column(name = "unit_amount")
    private BigDecimal valorUnitario;

    @Column(name = "total_amount")
    private BigDecimal valorTotal;

    public Comissao(final Taxa taxa, final BigDecimal valorUnitario, final Long quantidade) {
        this();
        this.taxa = taxa;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorUnitario.multiply(new BigDecimal(quantidade));
    }

    public Comissao() {}

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
