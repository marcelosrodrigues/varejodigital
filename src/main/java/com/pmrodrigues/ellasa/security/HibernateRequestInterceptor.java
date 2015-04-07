package com.pmrodrigues.ellasa.security;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.resource.ResourceMethod;
import com.pmrodrigues.ellasa.services.UsuarioService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static java.lang.String.format;

/**
 * Created by Marceloo on 01/04/2015.
 */
@Intercepts
@RequestScoped
public class HibernateRequestInterceptor implements Interceptor {

    private final static Logger logging = Logger.getLogger(HibernateRequestInterceptor.class);
    private final UsuarioService service;
    private final HttpServletRequest request;

    @Override
    public void intercept(final InterceptorStack stack, final ResourceMethod method, final Object resourceInstance)
            throws InterceptionException {

        logging.debug("iniciando a analise do perfil do usuário para aplicar a filtragem de dados");

        final String username = request.getRemoteUser();

        if (request.isUserInRole("VENDEDOR")) {
            logging.debug(format("%s é vendedor", username));
            service.filtrarPorVendedor(username);
        } else if (request.isUserInRole("LOJISTA")) {
            logging.debug(format("%s é lojista", username));
            service.filtrarPorLojista(username);
        }
        stack.next(method, resourceInstance);

    }

    @Override
    public boolean accepts(ResourceMethod method) {
        return true;
    }

    public HibernateRequestInterceptor(final UsuarioService service, final HttpServletRequest request) {
        this.service = service;
        this.request = request;
    }
}
