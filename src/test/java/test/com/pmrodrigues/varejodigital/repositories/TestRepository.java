package test.com.pmrodrigues.varejodigital.repositories;

import com.pmrodrigues.varejodigital.models.Estado;
import com.pmrodrigues.varejodigital.repositories.EstadoRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestRepository
        extends
        AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private EstadoRepository repository;

    private Estado estado;

    @Before
    public void before() {
        jdbcTemplate.update("delete from estado where uf = 'TT'");
    }


    @Test
    public void testFindById() {

        final Long id = this.jdbcTemplate.queryForObject("select id from estado where uf = 'RJ'", Long.class);
        Estado estado = repository.findById(id);

        assertNotNull(estado);

    }

    @Test
    public void testListAll() {

        Long count = this.jdbcTemplate.queryForObject("select count(1) from estado", Long.class);
        List<Estado> estadoList = repository.list();

        assertEquals(count, new Long(estadoList.size()));

    }

    @Test
    public void testAdd() {

        final Estado estado = new Estado();
        estado.setUf("TT");
        estado.setNome("TESTE");
        estado.setId(10000L);
        repository.add(estado);

        long quantidade = jdbcTemplate.queryForObject("select count(id) from estado where uf = 'TT'", Long.class);

        assertNotEquals(1L, quantidade);

    }

    @Test
    public void testSet() {

        jdbcTemplate.update("insert into estado (id , uf , nome) values ( 10000 , 'TT' , 'TESTE')");
        final Estado estado = repository.findById(10000L);
        estado.setNome("TESTE 2");
        repository.set(estado);

        long quantidade = jdbcTemplate.queryForObject("select count(id) from estado where nome = 'TESTE 2'", Long.class);

        assertEquals(1L, quantidade);

    }

    @Test
    public void testRemove() {

        jdbcTemplate.update("insert into estado (id , uf , nome) values ( 10000 , 'TT' , 'TESTE')");
        final Estado estado = repository.findById(10000L);

        repository.remove(estado);

        long quantidade = jdbcTemplate.queryForObject("select count(id) from estado where uf = 'TT'", Long.class);

        assertNotEquals(0L, quantidade);
    }


}
