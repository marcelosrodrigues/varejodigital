package com.pmrodrigues.ellasa.enumarations;

/**
 * Created by Marceloo on 08/11/2014.
 */
public enum StatusPagamento {

    COMPLETO(21) , AGUARDANDO_PAGAMENTO(22) , PAGAMENTO_APROVADO(23), PAGAMENTO_EM_ANALISE(24),
    CANCELADO(25), EM_ABERTO(26), DEVOLVIDA(27), ESTORNADO(28);

    private final int status;

    private StatusPagamento(int status) {
        this.status = status;
    }

    public int getStatusCode() {
        return status;
    }

}
