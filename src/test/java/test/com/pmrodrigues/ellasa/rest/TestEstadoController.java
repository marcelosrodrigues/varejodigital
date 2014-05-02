package test.com.pmrodrigues.ellasa.rest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import br.com.caelum.vraptor.util.test.MockResult;

import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;
import com.pmrodrigues.ellasa.rest.EstadoController;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestEstadoController
		extends
			AbstractTransactionalJUnit4SpringContextTests {
	
	private EstadoController service;

	@Autowired
	private EstadoRepository repository;
	
	@Before
	public void  setup() {
		service = new EstadoController(repository, new MockResult());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void deveListarTodasAsSecoes() {
		final Long count = this.jdbcTemplate
				.queryForLong("select count(1) from estado");

		final List<Estado> estados = service.estados();
		assertEquals( count , Long.valueOf(estados.size()));
		
		
	}

}
