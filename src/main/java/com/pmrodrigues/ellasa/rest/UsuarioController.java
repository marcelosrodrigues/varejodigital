package com.pmrodrigues.ellasa.rest;

import static java.lang.String.format;

import br.com.caelum.vraptor.*;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.caelum.vraptor.view.Results;

import com.pmrodrigues.ellasa.models.Usuario;
import com.pmrodrigues.ellasa.repositories.UsuarioRepository;

@Resource
public class UsuarioController {

	private final UsuarioRepository repository;

	private final Result result;

	private static final Logger logging = Logger.getLogger(UsuarioController.class);

	public UsuarioController(final UsuarioRepository repository,
			final Result result) {
		this.repository = repository;
		this.result = result;
	}
	
	@Get
	@Path("/meus-dados.json")
	public Usuario meusDados() {
		final Authentication userAuthenticated = SecurityContextHolder.getContext().getAuthentication();
		final UserDetails user = (UserDetails) userAuthenticated.getPrincipal();
		
		logging.debug(format("Pesquisando os dados do franqueado pelo email %s",user.getUsername()));
		
		final Usuario usuario = repository.findByEmail(user.getUsername());
		
		logging.debug("Usuario encontrado");
		result.use(Results.json())
			  .from(usuario)
			  .include("celular","residencial","endereco","endereco.estado")
			  .exclude("password","cleanPassword")
			  .serialize();
		
		return usuario;
	}
}
