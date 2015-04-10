package com.pmrodrigues.ellasa.resolvers.crud;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.resource.HttpMethod;

import java.lang.reflect.Method;

/**
 * Created by Marceloo on 09/04/2015.
 */
class DefaultRouteConfig implements RouteConfig {
    private final Method method;
    private HttpMethod httpmethod = HttpMethod.GET;
    private String url = "/%s/";

    public DefaultRouteConfig(final Method metodo) {
        this.method = metodo;
        if (metodo.isAnnotationPresent(Path.class)) {
            final Path path = metodo.getAnnotation(Path.class);
            url = path.value()[0];
        } else {
            url += metodo.getName() + ".do";
        }

        if (metodo.isAnnotationPresent(Post.class)) {
            httpmethod = HttpMethod.POST;
        }
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return httpmethod;
    }

    @Override
    public String getURL() {
        return url;
    }
}
