package test.com.pmrodrigues.varejodigital.taglib;

import br.com.caelum.vraptor.validator.ValidationMessage;
import com.pmrodrigues.varejodigital.taglib.SummaryTag;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.springframework.mock.web.MockJspWriter;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Marceloo on 23/01/2015.
 */
public class TestSummaryTag {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    private JspContext jspContext = context.mock(JspContext.class);
    private StringWriter writer = new StringWriter();


    @Test
    public void doTag() throws IOException, JspException {

        final ValidationMessage error = context.mock(ValidationMessage.class);
        final List<ValidationMessage> errors = new ArrayList<>();
        errors.add(error);

        context.checking(new Expectations() {{
            allowing(jspContext).getOut();
            will(returnValue(new MockJspWriter(writer)));

            allowing(jspContext).getAttribute("errors", PageContext.REQUEST_SCOPE);
            will(returnValue(errors));

            oneOf(error).getMessage();
            will(returnValue("teste"));
        }});

        final String EXPECTED = "<div class=\"alert alert-danger alert-dismissable\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">×</button><strong>teste</strong><br/><p>teste</p></div>";

        SummaryTag summary = new SummaryTag();
        summary.setText("teste");
        summary.setJspContext(jspContext);

        summary.doTag();
        assertEquals(EXPECTED, writer.toString());

    }

    @Test
    public void doTagWithOutText() throws IOException, JspException {

        context.checking(new Expectations() {{
            allowing(jspContext).getOut();
            will(returnValue(new MockJspWriter(writer)));

            allowing(jspContext).getAttribute("errors", PageContext.REQUEST_SCOPE);
            will(returnValue(null));
        }});

        SummaryTag summary = new SummaryTag();
        summary.setText("teste");
        summary.setJspContext(jspContext);
        summary.doTag();

        assertEquals("", writer.toString());
    }
}
