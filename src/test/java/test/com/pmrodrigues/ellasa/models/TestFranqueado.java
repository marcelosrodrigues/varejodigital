package test.com.pmrodrigues.ellasa.models;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import test.com.pmrodrigues.ellasa.Factory;

import com.pmrodrigues.ellasa.exceptions.EstouroTamanhoDeRedeException;
import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.models.Franqueado;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestFranqueado extends AbstractTransactionalJUnit4SpringContextTests {

	@PersistenceContext
	private EntityManager entityManager;

	private Estado estado;

	@Before
	public void before() {
		estado = entityManager.find(Estado.class, "RJ");
	}

	@After
	public void after() {

		Collection<Franqueado> franqueados = entityManager.createQuery(
				"from Franqueado", Franqueado.class).getResultList();
		for (Franqueado franqueado : franqueados) {
			entityManager.remove(franqueado);
		}

		entityManager.remove(estado);

	}

	@Test
	public void gerarCodigoIdentificao() {

		Franqueado franqueado = Factory.getStubFranqueado(estado);

		entityManager.persist(franqueado);

		Assert.assertNotNull(franqueado.getCodigo());
		Assert.assertNotNull(franqueado.getPassword());

	}

	@Test(expected = EstouroTamanhoDeRedeException.class)
	public void naoPermitirEntrarMaisQueAQuantidadePermitida()
			throws EstouroTamanhoDeRedeException {

		Franqueado franqueado = new Franqueado();

		for (int i = 0; i <= 6; i++) {
			franqueado.adicionar(new Franqueado());
		}

	}

}
