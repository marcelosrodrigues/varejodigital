package test.com.pmrodrigues.varejodigital.resolvers.crud;

import br.com.caelum.vraptor.http.route.Route;
import br.com.caelum.vraptor.http.route.Router;
import com.pmrodrigues.varejodigital.resolvers.crud.CRUDRoutes;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;

/**
 * Created by Marceloo on 09/04/2015.
 */
public class TestCRUDRoutes {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    @Test
    public void testConfig() throws Exception {

        final CRUDRoutes crudRoutes = new CRUDRoutes();
        final Router router = context.mock(Router.class);

        context.checking(new Expectations() {{
            allowing(router).builderFor(with(aNonNull(String.class)));
            allowing(router).add(with(aNonNull(Route.class)));
        }});

        crudRoutes.config(router);

    }
}