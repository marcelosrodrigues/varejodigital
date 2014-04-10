package test.com.pmrodrigues.ellasa.converter;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.pmrodrigues.ellasa.converters.TipoFranquiaConverter;
import com.pmrodrigues.ellasa.models.TipoFranquia;
import com.pmrodrigues.ellasa.repositories.TipoFranquiaRepository;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestTipoFranquiaConverter
		extends
			AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private TipoFranquiaRepository repository;

	private TipoFranquiaConverter converter;

	@Before
	public void setup() {
		converter = new TipoFranquiaConverter(repository);
	}

	@Test
	public void deveConverter() {

		final TipoFranquia franquia = converter.convert("4",
				TipoFranquia.class, null);

		assertNotNull(franquia);

	}

	@Test
	public void naoPodeConverterValorNulo() {

		final TipoFranquia franquia = converter.convert(null,
				TipoFranquia.class, null);

		assertNull(franquia);

	}


}
