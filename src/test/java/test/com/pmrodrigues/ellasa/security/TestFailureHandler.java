package test.com.pmrodrigues.ellasa.security;

import com.pmrodrigues.ellasa.security.FailureHandler;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.web.WebAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestFailureHandler {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    @Test
    public void falhaDeAutenticacaoParaAcessoAsPaginasDoSistema() throws Exception {

        final FailureHandler handler = new FailureHandler();
        final HttpServletRequest request = context.mock(HttpServletRequest.class);
        final HttpServletResponse response = context.mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = context.mock(RequestDispatcher.class);
        context.checking(new Expectations() {{

            oneOf(request).setAttribute(WebAttributes.ACCESS_DENIED_403, "teste");

            oneOf(request).getRequestURI();
            will(returnValue("teste.do"));

            oneOf(request).getRequestDispatcher("/login.jsp");
            will(returnValue(dispatcher));

            oneOf(dispatcher).forward(request, response);
        }});

        handler.onAuthenticationFailure(request, response, new CredentialsExpiredException("teste"));

    }

    @Test
    public void falhaDeAutenticacaoParaAcessoViaAndroid() throws Exception {
        final FailureHandler handler = new FailureHandler();
        final HttpServletRequest request = context.mock(HttpServletRequest.class);
        final HttpServletResponse response = context.mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = context.mock(RequestDispatcher.class);
        context.checking(new Expectations() {{

            oneOf(request).setAttribute(WebAttributes.ACCESS_DENIED_403, "teste");

            oneOf(request).getRequestURI();
            will(returnValue("teste.json"));

            oneOf(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "teste");

        }});

        handler.onAuthenticationFailure(request, response, new CredentialsExpiredException("teste"));
    }
}