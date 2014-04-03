package com.pmrodrigues.ellasa.resolvers;

import br.com.caelum.vraptor.http.FormatResolver;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.view.DefaultPathResolver;

import com.pmrodrigues.ellasa.annotations.Tiles;

@Component
public class TilesPathResolver extends DefaultPathResolver {

	public TilesPathResolver(FormatResolver resolver) {
		super(resolver);
	}

	@Override
	public String pathFor(ResourceMethod method) {

		if (method.getMethod().isAnnotationPresent(Tiles.class)) {
			return method.getMethod().getAnnotation(Tiles.class).definition();
		} else {
			return super.pathFor(method);
		}

	}

}
