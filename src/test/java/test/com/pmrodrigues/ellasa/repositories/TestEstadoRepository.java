package test.com.pmrodrigues.ellasa.repositories;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestEstadoRepository
		extends
			AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private EstadoRepository repository;

	@Test
	public void testFindById() {
		Estado estado = repository.findById("RJ");
		Assert.assertNotNull(estado);
	}

	@Test
	public void testList() {
		List<Estado> estados = repository.list();
		Assert.assertFalse(estados.isEmpty());
	}

}
