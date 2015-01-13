package com.pmrodrigues.ellasa.rest;

import br.com.caelum.vraptor.*;
import br.com.caelum.vraptor.view.Results;
import com.pmrodrigues.ellasa.models.Pedido;
import com.pmrodrigues.ellasa.models.Usuario;
import com.pmrodrigues.ellasa.repositories.UsuarioRepository;
import com.pmrodrigues.ellasa.services.PedidoService;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static java.lang.String.format;

/**
 * Created by Marceloo on 17/11/2014.
 */

@Resource
public class PagamentoController {

    private static final Logger logging = Logger.getLogger(PagamentoController.class);

    private final PedidoService service;
    private final Result result;
    private final UsuarioRepository repository;


    public PagamentoController(final PedidoService service, UsuarioRepository repository, final Result result) {
        this.service = service;
        this.repository = repository;
        this.result = result;
    }

    @Post
    @Path("/carrinho-de-compras/checkout.json")
    @Consumes("application/json")
    public Pedido pagar(final Pedido pedido) {

        final Authentication userAuthenticated = SecurityContextHolder.getContext().getAuthentication();
        final UserDetails user = (UserDetails) userAuthenticated.getPrincipal();

        logging.debug(format("Pesquisando os dados do franqueado pelo email %s", user.getUsername()));

        final Usuario usuario = repository.findByEmail(user.getUsername());

        pedido.associar(usuario);
        service.pagar(pedido);
        result.use(Results.json())
                .from(pedido)
                .include("dadosPagamento")
                .exclude("transportadora", "idioma", "carrinho", "moeda", "pagamento", "dataCompra", "dataEntrega", "dataCriacaco", "dataAlteracao", "valorPedido", "valorBruto", "valorLiquido", "totalPedido")
                .serialize();


        return pedido;

    }
}