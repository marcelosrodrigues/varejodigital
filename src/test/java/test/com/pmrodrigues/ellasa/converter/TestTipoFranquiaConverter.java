package test.com.pmrodrigues.ellasa.converter;

import com.pmrodrigues.ellasa.converters.TipoFranquiaConverter;
import com.pmrodrigues.ellasa.models.TipoFranquia;
import com.pmrodrigues.ellasa.repositories.TipoFranquiaRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
        jdbcTemplate.update("delete from tipofranquia where id = 99");
        jdbcTemplate.update("insert into tipofranquia (id , nome , descricao ) values ( 99 , 'TESTE' , 'TESTE')");
	}

    @After
    public void after() {
        jdbcTemplate.update("delete from tipofranquia where id = 99");
    }


	@Test
	public void deveConverter() {

		final TipoFranquia franquia = converter.convert("99",
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
