package test.com.pmrodrigues.varejodigital.crud;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import com.pmrodrigues.varejodigital.Constante;
import com.pmrodrigues.varejodigital.controllers.SecaoController;
import com.pmrodrigues.varejodigital.models.Imagem;
import com.pmrodrigues.varejodigital.models.Secao;
import com.pmrodrigues.varejodigital.repositories.SecaoRepository;
import com.pmrodrigues.varejodigital.sessionscope.Imagens;
import org.apache.commons.validator.GenericValidator;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestSecaoController extends AbstractTransactionalJUnit4SpringContextTests {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    @Autowired
    private SecaoRepository repository;

    private MockResult result = new MockResult();
    private MockValidator validator = new MockValidator();
    private Imagens imagens = context.mock(Imagens.class);
    private SecaoController controller;

    @Before
    public void before() {
        this.after();
        controller = new SecaoController(repository, result, validator, imagens);
    }

    @After
    public void after() {
        jdbcTemplate.query("select id from secao where secao = 'TESTE COM ICONE'", new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                jdbcTemplate.update("delete from secao where id = ?", rs.getLong("id"));
                return null;
            }
        });

        jdbcTemplate.update("update secao set icone = null where id = 212");
    }

    @Test
    public void testAdicionarSubCategoria() throws Exception {

        final Secao secao = repository.findById(212L);

        controller.adicionarSubCategoria(secao);

        assertNotNull(result.included(Constante.DEPARTAMENTOS));
        assertNotNull(result.included(Constante.OBJECT));


    }

    @Test
    public void insert() {

        final Set<Imagem> arquivos = new HashSet<>();
        arquivos.addAll(Arrays.asList(new Imagem("arquivo.gif")));

        context.checking(new Expectations() {{
            allowing(imagens).getImagens();
            will(returnValue(arquivos));

            oneOf(imagens).apagar();
        }});

        final Secao secao = new Secao();
        secao.setNome("TESTE COM ICONE");

        controller.insert(secao);

        assertNotNull(secao.getIcone());
        assertFalse(GenericValidator.isBlankOrNull(secao.getIcone()));

        Long id = jdbcTemplate.queryForObject("select id from secao where secao = 'TESTE COM ICONE'", Long.class);
        assertNotNull(id);
        assertEquals(id, secao.getId());
    }

    @Test
    public void updateAtualizandoComponenteIncluindoArquivo() {
        final Set<Imagem> arquivos = new HashSet<>();
        arquivos.addAll(Arrays.asList(new Imagem("arquivo.gif")));

        context.checking(new Expectations() {{
            allowing(imagens).getImagens();
            will(returnValue(arquivos));

            oneOf(imagens).apagar();
        }});

        final Secao secao = repository.findById(212L);

        controller.update(secao);

        String arquivo = jdbcTemplate.queryForObject("select icone from secao where id = 212", String.class);
        assertNotNull(arquivo);

    }

    @Test
    public void removerArquivo() {

        jdbcTemplate.update("update secao set icone = 'teste' where id = 212");
        final Secao secao = repository.findById(212L);

        controller.removerIcone(secao);

        String arquivo = jdbcTemplate.queryForObject("select icone from secao where id = 212", String.class);
        assertNull(arquivo);

    }
}