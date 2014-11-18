package com.pmrodrigues.ellasa.deserialization;

import br.com.caelum.vraptor.deserialization.JsonDeserializer;
import br.com.caelum.vraptor.http.ParameterNameProvider;
import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.serialization.xstream.XStreamBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import org.hibernate.collection.internal.PersistentSet;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Marceloo on 18/11/2014.
 */
@Component
public class CustomJSONDeserialization extends JsonDeserializer
{
    public CustomJSONDeserialization(ParameterNameProvider provider, TypeNameExtractor extractor, XStreamBuilder builder) {
        super(provider, extractor, builder);
    }

    @Override
    protected XStream getXStream() {
        final XStream stream = super.getXStream();
        stream.registerConverter(new CollectionConverter(stream.getMapper()) {
            @Override
            public boolean canConvert(Class type) {
                return ( Collection.class.isAssignableFrom(type) );
            }

            @Override
            public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
                Collection collection = new HashSet();
                populateCollection(reader, context, collection);
                return collection;
            }
        });

        return stream;
    }
}
