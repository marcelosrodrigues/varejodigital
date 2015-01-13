package test.com.pmrodrigues.ellasa.taglib;

import com.pmrodrigues.ellasa.taglib.InputTextTag;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.springframework.mock.web.MockJspWriter;

import javax.servlet.jsp.JspContext;
import java.io.StringWriter;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestInputTextTag {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    private JspContext jspContext = context.mock(JspContext.class);
    private StringWriter writer = new StringWriter();

    @Test
    public void doTagWithOutError() throws Exception {

        context.checking(new Expectations() {{
            allowing(jspContext).getOut();
            will(returnValue(new MockJspWriter(writer)));

            allowing(jspContext).getAttribute("errors");
            will(returnValue(null));
        }});


        final InputTextTag textview = new InputTextTag();
        textview.setJspContext(jspContext);
        String EXPECTED = "<div class=\"form-group \"><label for=\"object.nome\">Nome</label><input type=\"text\" value=\"\" id=\"object.nome\" name=\"object.nome\" class=\"form-control\" placeholder=\"Nome\" /></div>";

        textview.setId("object.nome");
        textview.setLabel("departamento.nome");

        textview.doTag();

        assertEquals(EXPECTED, writer.toString());

    }

    @Test
    public void doTagWithError() throws Exception {

        final Map<String, String> errors = context.mock(Map.class);

        context.checking(new Expectations() {{
            allowing(jspContext).getOut();
            will(returnValue(new MockJspWriter(writer)));

            allowing(jspContext).getAttribute("errors");
            will(returnValue(errors));

            allowing(errors).size();
            will(returnValue(1));

            allowing(errors).isEmpty();
            will(returnValue(Boolean.FALSE));

            allowing(errors).containsKey(with(aNonNull(String.class)));
            will(returnValue(Boolean.TRUE));
        }});


        final InputTextTag textview = new InputTextTag();
        textview.setJspContext(jspContext);
        String EXPECTED = "<div class=\"form-group has-error\"><label for=\"object.nome\">Nome</label><input type=\"text\" value=\"\" id=\"object.nome\" name=\"object.nome\" class=\"form-control\" placeholder=\"Nome\" /></div>";

        textview.setId("object.nome");
        textview.setLabel("departamento.nome");

        textview.doTag();

        assertEquals(EXPECTED, writer.toString());
    }
}