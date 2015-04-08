package com.pmrodrigues.ellasa.services;

import com.pmrodrigues.ellasa.models.Perfil;
import com.pmrodrigues.ellasa.models.Usuario;
import com.pmrodrigues.ellasa.repositories.UsuarioRepository;
import com.pmrodrigues.ellasa.repositories.utils.FilterName;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Service("userService")
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;

    private static final Logger logging = Logger.getLogger(UsuarioService.class);

    @Override
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {
		
		logging.debug(format("Pesquisando usuario com email %s",username));
		
		final Usuario usuario = repository.findByEmail(username);
		if( usuario != null ) {
            final List<GrantedAuthority> authorities = new ArrayList<>();
            for (final Perfil role : usuario.getRoles()) {
                authorities.add(role.getAuthority());
            }

            return new User(username, usuario.getPassword(), true, true, true, !usuario.isBloqueado(), authorities);
        }
		
		throw new UsernameNotFoundException(format("Usuario %s não encontrado ou senha invalida",username));
	}

    public void filtrarPorVendedor(final String username) {

        final Usuario usuario = repository.findByEmail(username);
        this.repository.enableFilter(FilterName.FILTRO_POR_VENDEDOR, usuario.getId());

    }

    public void atualizarTentativasFalhas(final UserDetails user) {

        final Usuario usuario = repository.findByEmail(user.getUsername());
        usuario.incrementarTentativasFalhas();

        repository.set(usuario);

    }

    public void filtrarPorLojista(final String username) {
        final Usuario usuario = repository.findByEmail(username);
        repository.enableFilter(FilterName.FILTRO_POR_LOJA, usuario.getId());
    }
}
