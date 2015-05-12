package test.com.pmrodrigues.varejodigital.integration.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.validator.GenericValidator;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.springframework.http.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Marceloo on 29/12/2014.
 */
public abstract class AbstractTestRest<E> extends AbstractTransactionalJUnit4SpringContextTests {


    private final Class<E> persistentClass;

    public AbstractTestRest() {
        final ParameterizedType type = (ParameterizedType) this.getClass()
                .getGenericSuperclass();
        this.persistentClass = (Class<E>) type.getActualTypeArguments()[0];
    }


    protected HttpHeaders createHeaders(final String username, final String password) {
        return new HttpHeaders() {
            {
                if (!GenericValidator.isBlankOrNull(username) && !GenericValidator.isBlankOrNull(password)) {
                    String auth = username + ":" + password;
                    byte[] encodedAuth = Base64.encodeBase64(
                            auth.getBytes(Charset.forName("US-ASCII")));
                    String authHeader = "Basic " + new String(encodedAuth);
                    set("Authorization", authHeader);
                }
                setContentType(MediaType.APPLICATION_JSON);
                set("Accept", "application/json");
            }
        };
    }

    protected List<E> list(final String url) throws JSONException {
        return this.list(url, "marsilvarodrigues@gmail.com", "12345678");
    }

    protected List<E> list(final String url, final String username, final String password) throws JSONException {

        final ResponseEntity<String> json = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity<String>(createHeaders(username, password)), String.class);
        final JSONObject object = new JSONObject(json.getBody());
        return toList(object.get("list"));
    }

    protected E post(final String url, final String username, final String password, final String value) throws JSONException {

        final HttpEntity<String> entity = new HttpEntity<>(value, createHeaders(username, password));

        return new RestTemplate().postForObject(url, entity, persistentClass);
    }

    protected List<E> toList(final Gson gson, final Object json) {
        List<E> elements = null;
        if (json instanceof JSONArray) {
            elements = Arrays.asList((E[]) gson.fromJson(json.toString(), Array
                    .newInstance(persistentClass, 0).getClass()));
        } else {
            elements = new ArrayList<E>();
            elements.add(gson.fromJson(json.toString(), persistentClass));
        }
        return elements;
    }

    protected List<E> toList(final Object json) {
        final Gson gson = new GsonBuilder().create();
        return this.toList(gson, json);
    }

    @Before
    @Transactional
    public void setup() {

        prepare();
        final Long estado = this.jdbcTemplate.queryForObject("select id from estado where uf = 'RJ'", Long.class);

        this.jdbcTemplate.update("insert into usuario (bloqueado, email, password, cpf, dataNascimento, bairro, " +
                        "                              cep, cidade, complemento, logradouro, numero, nomeCompleto, estado_id) " +
                        "   value (?, ?, md5(?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                false,
                "marsilvarodrigues@gmail.com",
                "12345678",
                "456.718.757-14",
                DateTime.now().toDate(),
                "Pechincha",
                "RIO DE JANEIRO",
                "RJ", "APTO 206", "ESTRADA CAMPO DA AREA", "84", "MARCELO DA SILVA RODRIGUES", estado
        );

        this.jdbcTemplate.update("commit");

    }

    private void prepare() {

        jdbcTemplate.query("select id , residencial_id , celular_id from usuario where email = 'marsilvarodrigues@gmail.com'", new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

                final Long userId = rs.getLong("id");
                final Long celularId = rs.getLong("celular_id");
                final Long residencialId = rs.getLong("residencial_id");

                AbstractTestRest.this.jdbcTemplate.update("delete from usuario where id = ?", userId);
                AbstractTestRest.this.jdbcTemplate.update("delete from telefone where id in (?,?)", celularId, residencialId);

                return null;
            }

        });

    }

    @After
    public void after() {
        prepare();
    }

    public E post(String url, String value) {

        final HttpEntity<String> entity = new HttpEntity<>(value, createHeaders(null, null));
        return new RestTemplate().postForObject(url, entity, persistentClass);
    }
}
