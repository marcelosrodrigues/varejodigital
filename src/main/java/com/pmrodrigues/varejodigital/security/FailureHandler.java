package com.pmrodrigues.varejodigital.security;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Marceloo on 25/03/2015.
 */
@Component
public class FailureHandler implements AuthenticationFailureHandler {

    private static final Logger logging = Logger.getLogger(FailureHandler.class);

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
                                        final AuthenticationException exception) throws IOException, ServletException {

        logging.debug("preparando o retorno para o cliente com o erro na autenticacao");

        request.setAttribute(WebAttributes.ACCESS_DENIED_403, exception.getMessage());

        final String url = request.getRequestURI();

        if (url.lastIndexOf(".json") < 0) {

            logging.debug("falha na autenticacao veio da administracao");

            final RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
        }

    }
}
