package com.pmrodrigues.ellasa.services;

import com.pmrodrigues.ellasa.enumarations.StatusPagamento;
import com.pmrodrigues.ellasa.models.Cliente;
import com.pmrodrigues.ellasa.models.Pedido;
import com.pmrodrigues.ellasa.repositories.*;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static java.lang.String.format;

/**
 * Created by Marceloo on 13/10/2014.
 */
@Service("PedidoService")
public class PedidoService {

    private static final Logger logging = Logger.getLogger(PedidoService.class);

    @Resource(name = "ClienteRepository")
    private ClienteRepository clienteRepository;

    @Resource(name = "TaxaRepository")
    private TaxaRepository taxaRepository;

    @Resource(name = "PedidoRepository")
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoFactory pagamentoService;

    @Resource(name = "ShoppingRepository")
    private ShoppingRepository lojaRepository;

    @Resource(name = "ProdutoRepository")
    private ProdutoRepository produtoRepository;

    @Transactional
    public void pagar(final Pedido pedido) {

        logging.info(format("salvando o pedido %s", pedido));

        final String CODIGO_TRANSACAO = RandomStringUtils.randomAlphanumeric(20).toUpperCase();
        final Cliente cliente = pedido.getCliente();

        if (!cliente.isNovo()) {
            updateCliente(pedido, cliente);
        }

        pedido.setCodigoTransacao(CODIGO_TRANSACAO);
        pedido.setEnderecoEntrega(cliente.getEndereco());
        pedido.calcula(taxaRepository.list());
        pedidoRepository.add(pedido);

        logging.debug("enviando o pagamento");
        pedido.getDadosPagamento().setPedido(pedido);
        pagamentoService.pagar(pedido.getDadosPagamento());

        if( pedido.getDadosPagamento().isSucesso() ) {
            pedido.setStatus(StatusPagamento.AGUARDANDO_PAGAMENTO);
        } else {
            pedido.setStatus(StatusPagamento.CANCELADO);
        }

    }

    protected void updateCliente(final Pedido pedido,final Cliente cliente) {
        final Cliente existed = clienteRepository.findById(cliente.getId());
        if( existed != null ) {
            existed.setPrimeiroNome(cliente.getPrimeiroNome());
            existed.setUltimoNome(cliente.getUltimoNome());
            existed.setEmail(cliente.getEmail());
            existed.setDataNascimento(cliente.getDataNascimento());

            existed.getEndereco().setEstado(cliente.getEndereco().getEstado());
            existed.getEndereco().setLogradouro(cliente.getEndereco().getLogradouro());
            existed.getEndereco().setBairro(cliente.getEndereco().getBairro());
            existed.getEndereco().setCidade(cliente.getEndereco().getCidade());
            existed.getEndereco().setCep(cliente.getEndereco().getCep());
            existed.getEndereco().setTelefone(cliente.getEndereco().getTelefone());
            existed.getEndereco().setCelular(cliente.getEndereco().getCelular());

            pedido.setCliente(existed);
        }
    }

}
