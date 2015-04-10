package com.pmrodrigues.ellasa.resolvers.crud;

import br.com.caelum.vraptor.resource.HttpMethod;

import java.lang.reflect.Method;
import java.util.ResourceBundle;

/**
 * Created by Marceloo on 09/04/2015.
 */
class CRUDRouteConfig implements RouteConfig {

    private static final ResourceBundle routes = ResourceBundle.getBundle("route");
    private final ResourceBundle httpmethods = ResourceBundle.getBundle("routes-httpmethod");
    private final Method method;
    private final String nomeMetodo;

    public CRUDRouteConfig(final Method metodo) {
        this.method = metodo;
        this.nomeMetodo = method.getName();
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.valueOf(httpmethods.getString(nomeMetodo));
    }

    @Override
    public String getURL() {
        return routes.getString(nomeMetodo);
    }
}
