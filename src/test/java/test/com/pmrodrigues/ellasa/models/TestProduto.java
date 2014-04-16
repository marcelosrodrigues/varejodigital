package test.com.pmrodrigues.ellasa.models;

import static org.junit.Assert.assertNotNull;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.pmrodrigues.ellasa.models.Produto;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestProduto extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	@Before
	public void before() {
		session = sessionFactory.openSession();
	}


	@Test
	public void tentarLerDados() {

		Produto produto = (Produto) session.get(Produto.class, 173L);
		assertNotNull(produto);

	}
	
}
