package com.pmrodrigues.ellasa.deserialization;

import br.com.caelum.vraptor.deserialization.JsonDeserializer;
import br.com.caelum.vraptor.http.ParameterNameProvider;
import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.serialization.xstream.XStreamBuilder;
import com.google.gson.*;
import com.pmrodrigues.ellasa.exceptions.ErroNaoDocumentoException;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by Marceloo on 18/11/2014.
 */
@Component
public class CustomJSONDeserialization extends JsonDeserializer {
    private final ParameterNameProvider paramNameProvider;
    private static final Logger logging = Logger.getLogger(CustomJSONDeserialization.class);

    public CustomJSONDeserialization(final ParameterNameProvider provider, final TypeNameExtractor extractor,
                                     final XStreamBuilder builder) {
        super(provider, extractor, builder);
        this.paramNameProvider = provider;
    }

    @Override
    public Object[] deserialize(final InputStream inputStream, final ResourceMethod method) {

        final Class<?>[] types = method.getMethod().getParameterTypes();

        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(inputStream)));
            String line = null;
            final StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            final JsonParser parser = new JsonParser();
            final JsonObject root = (JsonObject) parser.parse(buffer.toString());
            final Object[] params = new Object[types.length];
            final String[] parameterNames = paramNameProvider.parameterNamesFor(method.getMethod());

            for (int i = 0; i < types.length; i++) {
                final String name = parameterNames[i];
                final JsonElement node = root.get(name);
                if (node != null) {
                    params[i] = gson.fromJson(node, types[i]);
                }

            }

            return params;

        } catch (IOException e) {
            logging.fatal("erro ao deserializar a mensagem json " + e.getMessage(), e);
            throw new ErroNaoDocumentoException(e);
        }

    }
}
