package test.com.pmrodrigues.ellasa.converter;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.pmrodrigues.ellasa.converters.FranqueadoConverter;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.repositories.FranqueadoRepository;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestFranqueadoConverter
		extends
			AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private FranqueadoRepository repository;

	private FranqueadoConverter converter;

	@Before
	public void setup() {
		converter = new FranqueadoConverter(repository);
	}

	@Test
	public void devePesquisarPeloId() {
		final Franqueado franqueado = converter.convert("142",
				Franqueado.class, null);

		assertNotNull(franqueado);
	}

	@Test
	public void devePesquisarPeloCodigo() {
		final Franqueado franqueado = converter.convert("4RGF6OP4SS",
				Franqueado.class, null);

		assertNotNull(franqueado);
	}
}
