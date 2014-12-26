package test.com.pmrodrigues.ellasa.repositories;

import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestRepository
		extends
			AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private EstadoRepository repository;

	private Estado estado;


    @Test
    public void testFindById() {

        final Long id = this.jdbcTemplate.queryForObject("select id from estado where uf = 'RJ'",Long.class);
        Estado estado = repository.findById(id);

        assertNotNull(estado);

    }

    @Test
    public void testListAll() {

        Long count = this.jdbcTemplate.queryForObject("select count(1) from estado", Long.class);
        List<Estado> estadoList = repository.list();

        assertEquals(count,new Long(estadoList.size()));

    }




}
