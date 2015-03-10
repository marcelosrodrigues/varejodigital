package test.com.pmrodrigues.ellasa.repositories;


import com.pmrodrigues.ellasa.models.Loja;
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


    @Test
    public void listarTodasAsSecoesPorLoja() {

        final Long id = this.jdbcTemplate.queryForObject("select id from loja where nome = 'PROJETANDOO'", Long.class);
        final Loja loja = new Loja();
        loja.setId(id);

        final List<Secao> secoes = repository.findByLoja(loja);
        final Long count = this.jdbcTemplate.queryForObject("select count(id) from secao inner join areas_vendas on id = secao_id where produto_id = ? ", Long.class, id);
        assertEquals(count, Long.valueOf(secoes.size()));
    }

    @Test
    public void pesquisarSecoesPorNome() {

        final Long id = this.jdbcTemplate.queryForObject("select id from loja where nome = 'PROJETANDOO'", Long.class);
        final Loja loja = new Loja();
        loja.setId(id);

        final Long count = this.jdbcTemplate.queryForObject("select count(id) from secao inner join areas_vendas on id = secao_id where produto_id = ? and secao like 'Vest%'", Long.class, id);

        final List<Secao> secoes = repository.listByNome(loja, "Vest");
        assertEquals(count, Long.valueOf(secoes.size()));
    }

    @Test
    public void pesquisarSubSecoes() {
        final Long id = this.jdbcTemplate.queryForObject("select id from loja where nome = 'PROJETANDOO'", Long.class);
        final Loja loja = new Loja();
        loja.setId(id);

        final Long secaoId = this.jdbcTemplate.queryForObject("select max(id) from secao where pai_id is null ", Long.class);
        final Secao secao = new Secao();
        secao.setId(secaoId);

        final Long count = this.jdbcTemplate.queryForObject("select count(id) from secao inner join areas_vendas on id = secao_id where produto_id = ? and pai_id = ?", Long.class, id, secaoId);

        List<Secao> secoes = repository.listAllSubSecoesByLojaAndPai(loja, secao);
        assertEquals(count, Long.valueOf(secoes.size()));


    }
}