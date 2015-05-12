package test.com.pmrodrigues.varejodigital.taglib;

import com.pmrodrigues.varejodigital.repositories.SecaoRepository;
import com.pmrodrigues.varejodigital.taglib.TreeViewTag;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockJspWriter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.servlet.jsp.JspContext;
import java.io.StringWriter;

import static org.junit.Assert.assertFalse;

/**
 * Created by Marceloo on 09/01/2015.
 */
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestTreeViewTagLig
        extends
        AbstractTransactionalJUnit4SpringContextTests {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    private JspContext jspContext = context.mock(JspContext.class);
    private StringWriter writer = new StringWriter();

    @Autowired
    private SecaoRepository repository;

    @Before
    public void setup() {

        context.checking(new Expectations() {{
            allowing(jspContext).getOut();
            will(returnValue(new MockJspWriter(writer)));
        }});

    }

    @Test
    public void createSimpleTreeView() throws Exception {

        final TreeViewTag treeview = new TreeViewTag();
        treeview.setJspContext(jspContext);
        treeview.setList(repository.list());
        treeview.setValue("id");
        treeview.setLabel("nome");
        treeview.setUrl("/departamento/abrir.do");
        treeview.setSubList("subsecoes");

        treeview.doTag();

        assertFalse(writer.toString().length() == 0);

    }
}
