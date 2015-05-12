package com.pmrodrigues.varejodigital.security;

import com.pmrodrigues.varejodigital.models.Usuario;
import com.pmrodrigues.varejodigital.repositories.UsuarioRepository;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.String.format;

/**
 * Created by Marceloo on 26/03/2015.
 */
@Component
public class SuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logging = Logger.getLogger(SuccessHandler.class);

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) throws IOException, ServletException {

        final String email = (String) authentication.getPrincipal();

        logging.debug(format("acesso liberado ao sistema para o usuario %s", email));

        final Usuario usuario = repository.findByEmail(email);
        usuario.desbloquear();
        repository.set(usuario);

        String context = "";
        if (!GenericValidator.isBlankOrNull(request.getContextPath())) {
            context = request.getContextPath();
        }

        response.sendRedirect(context + "/index.do");
    }
}
