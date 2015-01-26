package com.pmrodrigues.ellasa.deserialization;

import br.com.caelum.vraptor.deserialization.JsonDeserializer;
import br.com.caelum.vraptor.http.ParameterNameProvider;
import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.serialization.xstream.XStreamBuilder;
import com.google.gson.*;

import java.io.*;

/**
 * Created by Marceloo on 18/11/2014.
 */
@Component
public class CustomJSONDeserialization extends JsonDeserializer {
    private final ParameterNameProvider paramNameProvider;

    public CustomJSONDeserialization(ParameterNameProvider provider, TypeNameExtractor extractor, XStreamBuilder builder) {
        super(provider, extractor, builder);
        this.paramNameProvider = provider;
    }

    @Override
    public Object[] deserialize(InputStream inputStream, ResourceMethod method) {

        Class<?>[] types = method.getMethod().getParameterTypes();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(inputStream)));
            String line = null;
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            JsonParser parser = new JsonParser();
            JsonObject root = (JsonObject) parser.parse(buffer.toString());
            Object[] params = new Object[types.length];
            String[] parameterNames = paramNameProvider.parameterNamesFor(method.getMethod());

            for (int i = 0; i < types.length; i++) {
                String name = parameterNames[i];
                JsonElement node = root.get(name);
                if (node != null) {
                    params[i] = gson.fromJson(node, types[i]);
                }

            }

            return params;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
