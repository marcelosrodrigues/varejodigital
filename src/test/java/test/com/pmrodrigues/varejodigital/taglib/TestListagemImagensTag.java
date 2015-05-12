package test.com.pmrodrigues.varejodigital.taglib;

import com.pmrodrigues.varejodigital.models.Imagem;
import com.pmrodrigues.varejodigital.taglib.ListagemImagensTag;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockJspWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestListagemImagensTag {
    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    private PageContext jspContext = context.mock(PageContext.class);
    private StringWriter writer = new StringWriter();
    private ListagemImagensTag tag = new ListagemImagensTag();

    @Before
    public void setup() {

        context.checking(new Expectations() {{
            oneOf(jspContext).getOut();
            will(returnValue(new MockJspWriter(writer)));
        }});

        tag.setJspContext(jspContext);
    }

    @Test
    public void testDoTag() throws IOException, JspException {

        final String expected = "<div><span id=\"0\">" +
                "<img src=\"http://localhost:8080/produtos/imagens/1.jpg\" width=\"150px\" height=\"150px\"/>" +
                "<br/><label onclick=\"javascript:deletarImagem(0);\">1.jpg</label></span></div>";

        List<Imagem> imagens = new ArrayList<>();
        Imagem imagem = new Imagem("1.jpg");
        imagem.setId(0L);
        imagens.add(imagem);
        tag.setImagens(imagens);

        tag.doTag();

        assertEquals(expected, writer.toString());

    }

    @Test
    public void testDoTagComImagensImportadasDoPrestashop() throws IOException, JspException {

        final String expected = "<div><span id=\"0\">" +
                "<img src=\"http://store.allinshopp.com.br/markao/api/images/products/133/155\" width=\"150px\" height=\"150px\"/>" +
                "<br/><label onclick=\"javascript:deletarImagem(0);\">155</label></span></div>";

        final List<Imagem> imagens = new ArrayList<>();
        final Imagem imagem = new Imagem();
        imagem.setUrl("http://store.allinshopp.com.br/markao/api/images/products/133/155");
        imagem.setId(0L);
        imagens.add(imagem);
        tag.setImagens(imagens);

        tag.doTag();

        assertEquals(expected, writer.toString());

    }
}