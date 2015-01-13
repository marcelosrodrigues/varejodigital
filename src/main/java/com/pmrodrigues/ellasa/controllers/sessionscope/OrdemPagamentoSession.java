package com.pmrodrigues.ellasa.controllers.sessionscope;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import com.pmrodrigues.ellasa.models.OrdemPagamento;

import java.io.Serializable;

@Component
@SessionScoped
public class OrdemPagamentoSession implements Serializable {

    private static final long serialVersionUID = 1L;

    private OrdemPagamento ordemPagamento;

    public OrdemPagamentoSession() {
    }

    public void toSession(final OrdemPagamento ordemPagamento) {
        this.ordemPagamento = ordemPagamento;
    }

    public OrdemPagamento fromSession() {
        return this.ordemPagamento;
    }
}
