package test.com.pmrodrigues.varejodigital.repositories;

import com.pmrodrigues.varejodigital.models.Perfil;
import com.pmrodrigues.varejodigital.repositories.PerfilRepository;
import com.pmrodrigues.varejodigital.repositories.ResultList;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestPerfilRepository extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private PerfilRepository repository;

    @Test
    public void pesquisarGrupos() {

        ResultList<Perfil> resultlist = repository.search(null);
        final Long count = jdbcTemplate.queryForObject("select count(1) from perfil", Long.class);
        assertEquals(count, resultlist.getRecordCount());

    }

    @Test
    public void pesquisarPorNome() {

        ResultList<Perfil> resultlist = repository.search(new Perfil("ADMIN"));
        final Long count = jdbcTemplate.queryForObject("select count(1) from perfil where nome like 'ADMIN%'", Long.class);
        assertEquals(count, resultlist.getRecordCount());

    }
}