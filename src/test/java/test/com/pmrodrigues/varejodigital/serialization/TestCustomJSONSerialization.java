package test.com.pmrodrigues.varejodigital.serialization;

import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.serialization.NullProxyInitializer;
import br.com.caelum.vraptor.serialization.ProxyInitializer;
import br.com.caelum.vraptor.serialization.Serializer;
import br.com.caelum.vraptor.serialization.xstream.XStreamBuilder;
import br.com.caelum.vraptor.serialization.xstream.XStreamBuilderImpl;
import br.com.caelum.vraptor.serialization.xstream.XStreamConverters;
import com.pmrodrigues.varejodigital.models.Estado;
import com.pmrodrigues.varejodigital.repositories.EstadoRepository;
import com.pmrodrigues.varejodigital.serialization.CustomJSONSerialization;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestCustomJSONSerialization extends
        AbstractTransactionalJUnit4SpringContextTests {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    @Autowired
    private EstadoRepository repository;

    @Test
    public void preparandoMensagemJSON() throws Exception {

        final TypeNameExtractor typeNameExtractor = context.mock(TypeNameExtractor.class);
        final XStreamBuilder xStreamBuilder = new XStreamBuilderImpl(new XStreamConverters(new ArrayList<Converter>(), new ArrayList<SingleValueConverter>()), typeNameExtractor);
        final ProxyInitializer proxy = new NullProxyInitializer();
        final HttpServletResponse response = context.mock(HttpServletResponse.class);
        final StringWriter stWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stWriter);
        context.checking(new Expectations() {{

            allowing(response).setContentType("application/json; charset=UTF-8");
            allowing(response).getWriter();
            will(returnValue(writer));

            allowing(typeNameExtractor).nameFor(Estado.class);
            will(returnValue("estado"));

        }});

        CustomJSONSerialization serialization = new CustomJSONSerialization(response, typeNameExtractor, proxy, xStreamBuilder);
        Serializer serializer = serialization.from(repository.list());
        serializer.serialize();

        final String EXPECTATION = "{\"list\": [{\"id\": 313,\"uf\": \"AC\",\"nome\": \"Acre\"},{\"id\": 314,\"uf\": \"AL\",\"nome\": \"Alagoas\"},{\"id\": 316,\"uf\": \"AM\",\"nome\": \"Amazonas\"},{\"id\": 315,\"uf\": \"AP\",\"nome\": \"Amapá\"},{\"id\": 317,\"uf\": \"BA\",\"nome\": \"Bahia\"},{\"id\": 318,\"uf\": \"CE\",\"nome\": \"Ceará\"},{\"id\": 339,\"uf\": \"DF\",\"nome\": \"Distrito Federal\"},{\"id\": 319,\"uf\": \"ES\",\"nome\": \"Espírito Santo\"},{\"id\": 320,\"uf\": \"GO\",\"nome\": \"Goiás\"},{\"id\": 321,\"uf\": \"MA\",\"nome\": \"Maranhão\"},{\"id\": 324,\"uf\": \"MG\",\"nome\": \"Minas Gerais\"},{\"id\": 323,\"uf\": \"MS\",\"nome\": \"Mato Grosso do Sul\"},{\"id\": 322,\"uf\": \"MT\",\"nome\": \"Mato Grosso\"},{\"id\": 325,\"uf\": \"PA\",\"nome\": \"Pará\"},{\"id\": 326,\"uf\": \"PB\",\"nome\": \"Paraíba\"},{\"id\": 328,\"uf\": \"PE\",\"nome\": \"Pernambuco\"},{\"id\": 329,\"uf\": \"PI\",\"nome\": \"Piauí\"},{\"id\": 327,\"uf\": \"PR\",\"nome\": \"Paraná\"},{\"id\": 330,\"uf\": \"RJ\",\"nome\": \"Rio de Janeiro\"},{\"id\": 331,\"uf\": \"RN\",\"nome\": \"Rio Grande do Norte\"},{\"id\": 333,\"uf\": \"RO\",\"nome\": \"Rondônia\"},{\"id\": 334,\"uf\": \"RR\",\"nome\": \"Roraima\"},{\"id\": 332,\"uf\": \"RS\",\"nome\": \"Rio Grande do Sul\"},{\"id\": 335,\"uf\": \"SC\",\"nome\": \"Santa Catarina\"},{\"id\": 337,\"uf\": \"SE\",\"nome\": \"Sergipe\"},{\"id\": 336,\"uf\": \"SP\",\"nome\": \"São Paulo\"},{\"id\": 338,\"uf\": \"TO\",\"nome\": \"Tocantins\"}]}";
        assertEquals(EXPECTATION, stWriter.getBuffer().toString());
    }
}