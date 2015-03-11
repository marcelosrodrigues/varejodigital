package com.pmrodrigues.ellasa.pagamentos.v1.status;

import com.pmrodrigues.ellasa.pagamentos.AkatusResponse;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Marceloo on 10/03/2015.
 */
public class StatusPagamentoResponse extends AkatusResponse {
    private BigDecimal valor;
    private Date dataCriacao;
    private Date atualizacao;

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setAtualizacao(Date atualizacao) {
        this.atualizacao = atualizacao;
    }

    public Date getAtualizacao() {
        return atualizacao;
    }
}
