package com.pmrodrigues.varejodigital.webservice;

import com.pmrodrigues.varejodigital.models.Produto;
import com.pmrodrigues.varejodigital.repositories.ProdutoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by Marceloo on 14/05/2015.
 */
@Service("Produtos")
@WebService(name = "Produtos" , portName = "ProdutosSOAP" , serviceName = "Produtos" , targetNamespace = "http://service.varejodigital.projetandoo/1.0/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT,use = SOAPBinding.Use.LITERAL,parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class ProdutoWebService {

    @Autowired
    private ProdutoRepository repository;

    private static final Logger logging = Logger.getLogger(ProdutoWebService.class);


    @WebMethod(action = "salvarProduto")
    public void salvar(@WebParam(name = "produto", mode = WebParam.Mode.IN, partName = "produto") final Produto produto) {

        logging.info("recebendo o produto " + produto + " para atualização");

        final Produto existed = repository.findByCodigoProduto(produto.getCodigoExterno());
        if( existed == null ) {
            repository.add(produto);
        } else {
            existed.setEstoque(produto.getEstoque());
            existed.setPreco(produto.getPreco());
            existed.setCusto(produto.getCusto());
            existed.setCodigoBarras(produto.getCodigoBarras());
            repository.set(existed);
        }

        logging.info("o produto " + produto + " foi salvo com sucesso");

    }

}
