package com.pmrodrigues.ellasa.services;

import com.pmrodrigues.ellasa.enumarations.StatusPagamento;
import com.pmrodrigues.ellasa.models.*;
import com.pmrodrigues.ellasa.repositories.*;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

/**
 * Created by Marceloo on 13/10/2014.
 */
@Service("PedidoService")
public class PedidoService {

    private static final Logger logging = Logger.getLogger(PedidoService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TaxaRepository taxaRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoFactory pagamentoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstadoRepository estadoRepository;


    @Transactional
    public void pagar(final Pedido pedido) {

        logging.info(format("salvando o pedido %s", pedido));

        final String CODIGO_TRANSACAO = RandomStringUtils.randomAlphanumeric(20).toUpperCase();
        final Cliente cliente = pedido.getCliente();
        final Cliente existente = clienteRepository.findByEmail(cliente.getEmail());
        final EnderecoCliente endereco = cliente.getEndereco();
        final Estado estado = endereco.getEstado();

        endereco.setEstado(estadoRepository.findById(estado.getId()));
        endereco.setCliente(cliente);

        if (existente != null) {
            updateCliente(pedido, existente);
        }

        for (final ItemPedido item : pedido.getItens()) {
            final Produto produto = item.getProduto();
            item.setProduto(produtoRepository.findById(produto.getId()));
        }

        pedido.setCodigoTransacao(CODIGO_TRANSACAO);
        pedido.setEnderecoEntrega(cliente.getEndereco());
        pedido.calcula(taxaRepository.list());
        pedidoRepository.add(pedido);

        logging.debug("enviando o pagamento");
        pedido.getDadosPagamento().setPedido(pedido);
        pagamentoService.pagar(pedido.getDadosPagamento());

        if (pedido.getDadosPagamento().isSucesso()) {
            pedido.setStatus(StatusPagamento.AGUARDANDO_PAGAMENTO);
        } else {
            pedido.setStatus(StatusPagamento.CANCELADO);
        }

    }

    private void updateCliente(final Pedido pedido, final Cliente cliente) {

        cliente.setPrimeiroNome(pedido.getCliente().getPrimeiroNome());
        cliente.setUltimoNome(pedido.getCliente().getUltimoNome());
        cliente.setEmail(pedido.getCliente().getEmail());
        cliente.setDataNascimento(pedido.getCliente().getDataNascimento());

        cliente.getEndereco().setEstado(pedido.getCliente().getEndereco().getEstado());
        cliente.getEndereco().setLogradouro(pedido.getCliente().getEndereco().getLogradouro());
        cliente.getEndereco().setBairro(pedido.getCliente().getEndereco().getBairro());
        cliente.getEndereco().setCidade(pedido.getCliente().getEndereco().getCidade());
        cliente.getEndereco().setCep(pedido.getCliente().getEndereco().getCep());
        cliente.getEndereco().setTelefone(pedido.getCliente().getEndereco().getTelefone());
        cliente.getEndereco().setCelular(pedido.getCliente().getEndereco().getCelular());

        pedido.setCliente(cliente);

    }

}
