package test.com.pmrodrigues.ellasa.repositories;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import test.com.pmrodrigues.ellasa.Factory;

import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.models.FranqueadoPessoaFisica;
import com.pmrodrigues.ellasa.repositories.FranqueadoRepository;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestRepository
		extends
			AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private FranqueadoRepository repository;

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

	}

	@Test
	public void deveInserir() {

		Franqueado franqueado = Factory.getStubFranqueado(estado);

		repository.add(franqueado);

		Assert.assertNotSame(0L, franqueado.getId());
	}

	@Test
	public void deveAlterar() {

		Franqueado franqueado = Factory.getStubFranqueado(estado);

		repository.add(franqueado);

		FranqueadoPessoaFisica toUpdate = entityManager.find(FranqueadoPessoaFisica.class,
				franqueado.getId());
		entityManager.detach(toUpdate);

		toUpdate.setNomeCompleto("Marcelo");
		repository.set(franqueado);

	}

	@Test
	public void deveRemover() {

		Franqueado franqueado = Factory.getStubFranqueado(estado);
		repository.add(franqueado);

		repository.remove(franqueado);

		Franqueado isDeleted = entityManager.find(Franqueado.class,
				franqueado.getId());
		Assert.assertNull(isDeleted);
	}

	@Test
	public void deveEncontrarPelaChave() {

		Franqueado franqueado = Factory.getStubFranqueado(estado);
		repository.add(franqueado);

		Franqueado toFound = repository.findById(franqueado.getId());
		
		Assert.assertNotNull(toFound);
	}

	@Test
	public void deveListarTodosOsValores() {
		Franqueado franqueado = Factory.getStubFranqueado(estado);

		repository.add(franqueado);

		List<Franqueado> franqueados = repository.list();
		Assert.assertFalse(franqueados.isEmpty());
	}

	@Test
	public void naoDeveEncontrarPelaChave() {
		Assert.assertNull(repository.findById(0L));
	}

}
