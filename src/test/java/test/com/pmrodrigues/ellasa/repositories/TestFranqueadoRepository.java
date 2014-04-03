package test.com.pmrodrigues.ellasa.repositories;

import java.util.Collection;

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
import com.pmrodrigues.ellasa.repositories.EstadoRepository;
import com.pmrodrigues.ellasa.repositories.FranqueadoRepository;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestFranqueadoRepository
		extends
			AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private FranqueadoRepository repository;

	@Autowired
	private EstadoRepository estadoRepository;

	private Estado estado;

	@Before
	public void before() {

		estado = estadoRepository.findById("RJ");
	}

	@After
	public void after() {

		Collection<Franqueado> franqueados = repository.list();
		for (Franqueado franqueado : franqueados) {
			repository.remove(franqueado);
		}

	}

	@Test
	public void deveEcontrarFranqueadoPeloCodigo() {

		Franqueado franqueado = Factory.getStubFranqueado(estado);

		repository.add(franqueado);

		Franqueado founded = repository.findByCodigo(franqueado.getCodigo());

		Assert.assertNotNull(founded);
	}
}
