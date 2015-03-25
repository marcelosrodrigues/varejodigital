package com.pmrodrigues.ellasa.services;

import com.pmrodrigues.ellasa.factory.PayerFactory;
import com.pmrodrigues.ellasa.models.ItemPedido;
import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.pagamentos.Akatus;
import com.pmrodrigues.ellasa.pagamentos.Akatus.Environment;
import com.pmrodrigues.ellasa.pagamentos.entity.Payer;
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction;
import com.pmrodrigues.ellasa.pagamentos.v1.cart.CartOperation;
import com.pmrodrigues.ellasa.pagamentos.v1.cart.CartResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;


@PropertySource("classpath:akatus.properties")
public abstract class AbstractPagamentoService implements PagamentoService {

    private final static Logger LOGGER = Logger
            .getLogger(AbstractPagamentoService.class);

    private CartOperation carrinho;

    @Value("${AUTH_USER}")
    private String user;

    @Value("${AUTH_PASSWORD}")
    private String key;

    @Value("${AKATUR_URL}")
    private String enviroment;

    protected Recebedor getRecebedor() {
        return new Recebedor(user, key);
    }

    protected Transaction criarTransacao(final OrdemPagamento pagamento) {
        final Recebedor recebedor = this.getRecebedor();
        this.carrinho = new Akatus(Environment.valueOf(enviroment),
                recebedor.getEmail(),
                recebedor.getApiKey()).cart();

        final Payer pagador = PayerFactory.getInstance(pagamento)
                .create();

        carrinho.setPayer(pagador);
        if (pagamento.getPedido() == null) {
            carrinho.addProduct(pagamento.getCarrinho(), pagamento.getDescricao(),
                    pagamento.getValor().doubleValue(), 0D, 1, 0D);
        } else {
            for (final ItemPedido item : pagamento.getPedido().getItens()) {
                carrinho.addProduct(item.getProduto().getId().toString(), item.getProduto().getNome(),
                        item.getProduto().getPreco().doubleValue(), item.getProduto().getPeso().doubleValue(), item.getQuantidade().intValue());
            }
        }

        final Transaction trans = carrinho.getTransaction();
        trans.setReference(pagamento.getCarrinho());
        trans.setInstallments(1);

        return trans;
    }

    protected void execute(final OrdemPagamento pagamento) {

        LOGGER.info("Mandando ordem de pagamento para a akatus");
        final CartResponse response = (CartResponse) this.carrinho.execute();
        LOGGER.info("Ordem enviada com sucesso");
        pagamento.setCodigo(response.getTransaction());
        pagamento.setStatus(response.getStatus());
        pagamento.setMotivo(response.getDescription());
        pagamento.setDocumento(response.getReturnURL());

    }

}
