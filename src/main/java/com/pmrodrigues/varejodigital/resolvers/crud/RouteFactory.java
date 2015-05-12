package com.pmrodrigues.varejodigital.resolvers.crud;

import java.lang.reflect.Method;
import java.util.ResourceBundle;

/**
 * Created by Marceloo on 09/04/2015.
 */
final class RouteFactory {

    private static final ResourceBundle routes = ResourceBundle.getBundle("route");

    private RouteFactory() {
    }

    public static RouteConfig create(final Method metodo) {

        final String nomeMetodo = metodo.getName();
        if (routes.containsKey(nomeMetodo)) {
            return new CRUDRouteConfig(metodo);
        } else {
            return new DefaultRouteConfig(metodo);
        }

    }
}
