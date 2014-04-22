package test.com.pmrodrigues.ellasa.rest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import br.com.caelum.vraptor.util.test.MockResult;

import com.pmrodrigues.ellasa.models.CEP;
import com.pmrodrigues.ellasa.repositories.CEPRepository;
import com.pmrodrigues.ellasa.rest.CEPController;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestCEPController
		extends
			AbstractTransactionalJUnit4SpringContextTests {
	
	private CEPController service;

	@Autowired
	private CEPRepository repository;
	
	@Before
	public void  setup() {
		service = new CEPController(repository, new MockResult());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void deveListarTodasAsSecoes() {
		final Long count = this.jdbcTemplate
				.queryForLong("select count(1) from cep");

		final List<CEP> ceps = service.ceps();
		assertEquals( count , new Long(ceps.size()));
		
		
	}

}
