package com.pmrodrigues.ellasa.resolvers.crud;

import br.com.caelum.vraptor.http.route.Router;
import br.com.caelum.vraptor.http.route.RoutesConfiguration;
import br.com.caelum.vraptor.http.route.Rules;
import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import com.pmrodrigues.ellasa.controllers.crud.AbstractCRUDController;
import org.apache.log4j.Logger;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ResourceBundle;
import java.util.Set;

import static java.lang.String.format;

/**
 * Created by Marceloo on 09/12/2014.
 */
@Component
@ApplicationScoped
public class CRUDRoutes implements RoutesConfiguration {

    private static final Logger logging = Logger.getLogger(CRUDRoutes.class);
    private final ResourceBundle routes = ResourceBundle.getBundle("route");
    private final ResourceBundle httpmethods = ResourceBundle.getBundle("routes-httpmethod");
    @Override
    public void config(final Router router) {

        final Reflections reflections = new Reflections("com.pmrodrigues");
        final Set<Class<? extends AbstractCRUDController>> classes = reflections.getSubTypesOf(AbstractCRUDController.class);

        new Rules(router) {
            @Override
            public void routes() {
                for (final Class<? extends AbstractCRUDController> clazz : classes) {

                    final ParameterizedType type = (ParameterizedType) clazz.getGenericSuperclass();
                    final Class persistedType = (Class) type.getActualTypeArguments()[0];
                    final String entityName = persistedType.getSimpleName().toLowerCase();

                    for (final Method metodo : clazz.getMethods()) {

                        if (!metodo.getDeclaringClass().equals(Object.class)) {
                            RouteConfig config = RouteFactory.create(metodo);

                            routeFor(format(config.getURL(), entityName))
                                    .with(config.getHttpMethod())
                                    .is(clazz, config.getMethod());
                        }

                    }

                }
            }
        };
    }
}
