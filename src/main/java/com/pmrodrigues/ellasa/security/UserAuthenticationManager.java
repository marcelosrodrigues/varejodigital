package com.pmrodrigues.ellasa.security;

import com.pmrodrigues.ellasa.services.UserService;
import com.pmrodrigues.ellasa.utilities.MD5;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import static java.lang.String.format;

/**
 * Created by Marceloo on 24/03/2015.
 */
public class UserAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserService userService;

    private static final Logger logging = Logger.getLogger(UserAuthenticationManager.class);


    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

        logging.debug(format("iniciando a autenticacao do usuario %s", authentication.getPrincipal()));

        final UserDetails user = userService.loadUserByUsername((String) authentication.getPrincipal());

        if (user.isAccountNonLocked()) {
            String password = (String) authentication.getCredentials();
            password = MD5.encrypt(password);
            if (password.equals(user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
            } else {
                userService.atualizarTentativasFalhas(user);
                throw new BadCredentialsException(format("Usuario %s não encontrado ou senha invalida", authentication.getPrincipal()));
            }
        } else {
            throw new LockedException("Seu acesso foi bloqueado. Favor encontrar em contato com o administrador do sistema");
        }
    }
}
