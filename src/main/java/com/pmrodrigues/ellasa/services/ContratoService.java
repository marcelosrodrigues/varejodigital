package com.pmrodrigues.ellasa.services;

import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.models.Usuario;
import com.pmrodrigues.ellasa.repositories.ContratoRepository;
import com.pmrodrigues.ellasa.repositories.OrdemPagamentoRepository;
import com.pmrodrigues.ellasa.repositories.UsuarioRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

@Service
public class ContratoService {

    @Autowired
    private PagamentoFactory factory;

    @Resource(name = "OrdemPagamentoRepository")
    private OrdemPagamentoRepository ordempagamentoRepository;

    @Resource(name = "ContratoRepository")
    private ContratoRepository repository;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private EmailService email;

    private static final Logger logging = Logger.getLogger(ContratoService.class);

    @Transactional(propagation = Propagation.REQUIRED)
    public void assinar(final OrdemPagamento ordempagamento) {

        final PagamentoService caixa = factory
                .getPagamentoService(ordempagamento.getMeioPagamento()
                        .getTipo());

        caixa.pagar(ordempagamento);
        if (ordempagamento.isSucesso()) {
            this.adicionar(ordempagamento.getContrato().getFranqueado());
            repository.add(ordempagamento.getContrato());
            ordempagamentoRepository.add(ordempagamento);
        } else {
            throw new RuntimeException(ordempagamento.getMotivo());
        }

    }

    @Transactional
    public void adicionar(final Usuario franqueado) {

        userRepository.add(franqueado);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("franqueado", franqueado);

        email.from("sac@catalogodigitalellasa.com.br")
                .to(franqueado.getEmail())
                .subject("Seja bem-vindo a Ella S/A")
                .template("/templates/novosfranqueados.vm", parameters).send();

        logging.debug(format("Franquado %s adicionado com sucesso", franqueado));

    }
}
