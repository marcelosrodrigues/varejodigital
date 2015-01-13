package com.pmrodrigues.ellasa.serialization;

import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.serialization.ProxyInitializer;
import br.com.caelum.vraptor.serialization.xstream.XStreamBuilder;
import br.com.caelum.vraptor.serialization.xstream.XStreamJSONSerialization;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import org.hibernate.collection.internal.PersistentSet;

import javax.servlet.http.HttpServletResponse;

@Component
public class CustomJSONSerialization extends XStreamJSONSerialization {

    public CustomJSONSerialization(final HttpServletResponse response,
                                   final TypeNameExtractor extractor,
                                   final ProxyInitializer initializer, XStreamBuilder builder) {
        super(response, extractor, initializer, builder);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected XStream getXStream() {

        final XStream stream = super.getXStream();
        stream.addDefaultImplementation(java.sql.Date.class, java.util.Date.class);
        stream.registerConverter(new CollectionConverter(stream.getMapper()) {
            @Override
            public boolean canConvert(Class type) {
                return PersistentSet.class.isAssignableFrom(type);
            }
        });

        return stream;
    }


}
