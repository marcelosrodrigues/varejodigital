package com.pmrodrigues.varejodigital.webservice;

import com.pmrodrigues.varejodigital.models.Produto;
import com.pmrodrigues.varejodigital.repositories.ProdutoRepository;
import com.pmrodrigues.varejodigital.webservice.dto.ProdutoResponseType;
import com.pmrodrigues.varejodigital.webservice.dto.Status;
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
    public ProdutoResponseType salvar(@WebParam(name = "produto", mode = WebParam.Mode.IN, partName = "produto") final Produto produto) {

        logging.info("recebendo o produto " + produto + " para atualização");
        ProdutoResponseType response = null;
        final Produto existed = repository.findByCodigoProduto(produto.getCodigoExterno());
        if( existed == null ) {
            logging.trace("produto novo");

            repository.add(produto);
            response = new ProdutoResponseType(Status.SUCESSO,produto,"Produto adicionado com sucesso");
        } else {

            logging.trace("produto já existe no banco de dados");

            existed.setEstoque(produto.getEstoque());
            existed.setPreco(produto.getPreco());
            existed.setCusto(produto.getCusto());
            existed.setCodigoBarras(produto.getCodigoBarras());
            repository.set(existed);
            response = new ProdutoResponseType(Status.SUCESSO,existed,"Produto alterado com sucesso");
        }

        logging.info("o produto " + produto + " foi salvo com sucesso");

        return response;
    }

}
