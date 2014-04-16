package test.com.pmrodrigues.ellasa.rest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import br.com.caelum.vraptor.util.test.MockResult;

import com.pmrodrigues.ellasa.models.Produto;
import com.pmrodrigues.ellasa.repositories.ProdutoRepository;
import com.pmrodrigues.ellasa.rest.ProdutoController;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestProdutoController
		extends
			AbstractTransactionalJUnit4SpringContextTests {
	
	private ProdutoController service;

	@Autowired
	private ProdutoRepository repository;
	
	@Before
	public void  setup() {
		service = new ProdutoController(repository, new MockResult());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void deveListarTodasAsSecoes() {
		final Long count = this.jdbcTemplate
				.queryForLong("select count(1) from produto");

		final List<Produto> produtos = service.produtos();
		assertEquals( count , new Long(produtos.size()));
		
		
	}

}
