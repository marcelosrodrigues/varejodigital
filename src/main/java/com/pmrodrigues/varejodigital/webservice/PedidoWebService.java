package com.pmrodrigues.varejodigital.webservice;

import com.pmrodrigues.varejodigital.models.ItemPedido;
import com.pmrodrigues.varejodigital.models.Pedido;
import com.pmrodrigues.varejodigital.models.Produto;
import com.pmrodrigues.varejodigital.repositories.PedidoRepository;
import com.pmrodrigues.varejodigital.repositories.ProdutoRepository;
import com.pmrodrigues.varejodigital.webservice.dto.PedidoRequestType;
import com.pmrodrigues.varejodigital.webservice.dto.PedidoResponseType;
import com.pmrodrigues.varejodigital.webservice.dto.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by Marceloo on 25/05/2015.
 */
@Service("Vendas")
@WebService(name = "Vendas" , portName = "VendasSOAP" , serviceName = "Vendas" , targetNamespace = "http://service.varejodigital.projetandoo/1.0/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT,use = SOAPBinding.Use.LITERAL,parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class PedidoWebService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @WebMethod(action = "salvarVendas")
    public PedidoResponseType salvarVendas(@WebParam(name = "pedido", mode = WebParam.Mode.IN, partName = "pedido") final PedidoRequestType venda) {

        final Pedido pedido = venda.novoPedido();

        for(final ItemPedido item : pedido.getItens() ) {
            final Produto produto = produtoRepository.findByCodigoProduto(item.getProduto().getCodigoExterno());
            if( produto == null ) {
                return new PedidoResponseType(Status.ERROR , "Produto não cadastrado");
            }
            item.setProduto(produto);
        }

        repository.add(pedido);
        return new PedidoResponseType(Status.SUCESSO , "Pedido salvo com sucesso");

    }
}
