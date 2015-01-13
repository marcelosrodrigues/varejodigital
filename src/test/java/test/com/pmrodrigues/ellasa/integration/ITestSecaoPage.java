package test.com.pmrodrigues.ellasa.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import test.com.pmrodrigues.ellasa.integration.pageobjects.tags.NovaSecaoPage;

import static org.junit.Assert.assertEquals;

/**
 * Created by Marceloo on 09/01/2015.
 */

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class ITestSecaoPage extends AbstractPage {


    @Before
    public void before() {

        this.jdbcTemplate.execute("delete from secao where secao like 'teste%'");

    }


    @Test
    public void adicionarRaiz() throws Exception {

        final NovaSecaoPage novo = (NovaSecaoPage) this.index()
                                .to(NovaSecaoPage.class);

        novo.nome("TESTE")
            .submit();

        long count = this.jdbcTemplate.queryForObject("select count(*) from secao where secao = 'TESTE'",Long.class);
        assertEquals(1L,count);

    }

    @Test
    public void adicionarFilho() throws Exception {


        final NovaSecaoPage novo = (NovaSecaoPage) this.index()
                .to(NovaSecaoPage.class);


        String pai = this.jdbcTemplate.queryForObject("select max(id) from secao",String.class);


        novo.nome("TESTE")
            .pai(pai)
            .submit();

        long count = this.jdbcTemplate.queryForObject("select count(*) from secao where secao = 'TESTE' and pai_id = ?",Long.class,Long.parseLong(pai));
        assertEquals(1L,count);

    }

    @After
    public void after() {
        super.after();
    }


}
