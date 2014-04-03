package test.com.pmrodrigues.ellasa.services;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.services.EmailService;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestEmailService extends AbstractJUnit4SpringContextTests {

	@Autowired
	private EmailService email;
	private Franqueado franqueado;

	@Before
	public void setup() throws Exception {
		this.franqueado = new Franqueado();
		Field nome = franqueado.getClass().getDeclaredField("nomeCompleto");
		nome.setAccessible(true);
		nome.set(franqueado, "Marcelo da Silva Rodrigues");

		Field codigo = franqueado.getClass().getDeclaredField("codigo");
		codigo.setAccessible(true);
		codigo.set(franqueado, RandomStringUtils.randomAlphanumeric(10)
				.toUpperCase());
	}

	@Test
	public void testSend() throws Exception {

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("franqueado", franqueado);

		email.from("novosfranqueados@ellasa.com.br")
				.to("marcelosrodrigues@globo.com")
				.subject("Seja bem-vindo a Ella S/A")
				.cc("marcelosrodrigues@globo.com")
				.template("/templates/novosfranqueados.vm", parameters).send();

	}

}
