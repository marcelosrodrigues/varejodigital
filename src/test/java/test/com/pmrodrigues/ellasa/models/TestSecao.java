package test.com.pmrodrigues.ellasa.models;

import static org.junit.Assert.assertNotNull;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.pmrodrigues.ellasa.models.Secao;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestSecao extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	@Before
	public void before() {
		session = sessionFactory.openSession();
	}

	@After
	public void after() {
		session.close();
		sessionFactory.close();
	}

	@Test
	public void tentarLerDados() {

		Secao secao = (Secao) session.get(Secao.class, 152L);
		assertNotNull(secao);

	}
}
