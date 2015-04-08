package com.pmrodrigues.ellasa.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Marceloo on 14/10/2014.
 */
@Embeddable
public class Comissao implements Serializable {


    @ManyToOne(optional = false)
    @JoinColumn(name = "taxa_id")
    private Taxa taxa; //NOPMD

    @Column
    private BigDecimal valorUnitario; //NOPMD

    @Column
    private BigDecimal valorTotal;

    public Comissao(final Taxa taxa, final BigDecimal valorUnitario, final Long quantidade) {
        this();
        this.taxa = taxa;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorUnitario.multiply(new BigDecimal(quantidade));
    }

    public Comissao() {
        //NOPMD
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
