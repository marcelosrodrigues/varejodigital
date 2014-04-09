package com.pmrodrigues.ellasa.services;

import static java.lang.String.format;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pmrodrigues.ellasa.exceptions.EstouroTamanhoDeRedeException;
import com.pmrodrigues.ellasa.exceptions.IndicacaoFranqueadoNaoEncontradoException;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.repositories.FranqueadoRepository;

@Service("FranqueadoService")
public class FranqueadoService {

	private static final Logger LOGGER = Logger
			.getLogger(FranqueadoService.class);

	@Resource(name = "FranqueadoRepository")
	private FranqueadoRepository repository;

	@Autowired
	private EmailService email;

	public void adicionar(final Franqueado franqueado, final String indicacao)
			throws IndicacaoFranqueadoNaoEncontradoException,
			EstouroTamanhoDeRedeException {

		LOGGER.debug(format(
				"Adicionando um novo franqueado %s indicado por %s indicacao",
				franqueado, indicacao));

		Franqueado quemIndicou = repository.findByCodigo(indicacao);

		if (quemIndicou == null) {
			throw new IndicacaoFranqueadoNaoEncontradoException(
					"Não foi possível efetuar o seu cadastro. Não encontramos quem te indicou no sistema");
		}

		quemIndicou.adicionar(franqueado);
		repository.add(franqueado);
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("franqueado", franqueado);
		
		email.from("sac@catalogodigitalellasa.com.br")
				.to(franqueado.getEmail())
			 .subject("Seja bem-vindo a Ella S/A")
			 .cc(quemIndicou.getEmail())
				.template("/templates/novosfranqueados.vm", parameters).send();

		LOGGER.debug(format("Franquado %s adicionado com sucesso", franqueado));

	}

	public Franqueado findByCodigo(final String codigo) {
		return repository.findByCodigo(codigo);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void adicionar(Franqueado franqueado) {
		repository.add(franqueado);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("franqueado", franqueado);

		email.from("sac@catalogodigitalellasa.com.br")
				.to(franqueado.getEmail())
				.subject("Seja bem-vindo a Ella S/A")
				.template("/templates/novosfranqueados.vm", parameters).send();

		LOGGER.debug(format("Franquado %s adicionado com sucesso", franqueado));

	}

}
