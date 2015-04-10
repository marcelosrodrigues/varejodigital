package com.pmrodrigues.ellasa.resolvers.crud;

import br.com.caelum.vraptor.resource.HttpMethod;

import java.lang.reflect.Method;

/**
 * Created by Marceloo on 09/04/2015.
 */
interface RouteConfig {

    Method getMethod();

    HttpMethod getHttpMethod();

    String getURL();

}
