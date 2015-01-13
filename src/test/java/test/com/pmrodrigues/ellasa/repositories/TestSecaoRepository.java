package test.com.pmrodrigues.ellasa.repositories;


import com.pmrodrigues.ellasa.models.Secao;
import com.pmrodrigues.ellasa.repositories.ResultList;
import com.pmrodrigues.ellasa.repositories.SecaoRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestSecaoRepository extends
        AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private SecaoRepository repository;

    @Test
    public void search() throws Exception {
        Long count = jdbcTemplate.queryForObject("select count(*) from secao where pai_id is null",Long.class);
        final ResultList<Secao> secoes = repository.search(null);
        assertEquals(count, secoes.getRecordCount());
    }

    @Test
    public void listAll() throws Exception {

        int count = jdbcTemplate.queryForObject("select count(*) from secao",Integer.class);
        final List<Secao> secoes = repository.listAll();
        assertEquals(count, secoes.size());

    }
}