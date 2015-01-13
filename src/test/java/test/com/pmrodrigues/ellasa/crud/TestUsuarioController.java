package test.com.pmrodrigues.ellasa.crud;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import com.pmrodrigues.ellasa.Constante;
import com.pmrodrigues.ellasa.controllers.UsuarioController;
import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.models.Telefone;
import com.pmrodrigues.ellasa.models.Usuario;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;
import com.pmrodrigues.ellasa.repositories.UsuarioRepository;
import com.pmrodrigues.ellasa.services.EmailService;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Marceloo on 02/01/2015.
 */
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestUsuarioController extends
        AbstractTransactionalJUnit4SpringContextTests {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };


    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    private MockResult result = new MockResult();
    private MockValidator validator = new MockValidator();
    private UsuarioController controller;
    private EmailService email = context.mock(EmailService.class);

    private Long userId;

    @Resource(name = "org.springframework.security.authenticationManager")
    private AuthenticationManager authenticationManager;

    @Before
    public void before() {
        controller = new UsuarioController(userRepository, estadoRepository, email, result, validator);


        prepare();
        final Long estado = this.jdbcTemplate.queryForObject("select id from estado where uf = 'RJ'", Long.class);

        this.jdbcTemplate.update("insert into usuario (bloqueado, email, password, cpf, dataNascimento, bairro, " +
                        "                              cep, cidade, complemento, logradouro, numero, nomeCompleto, estado_id) " +
                        "   value (?, ?, md5(?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                false,
                "marsilvarodrigues@gmail.com",
                "12345678",
                "456.718.757-14",
                DateTime.now().toDate(),
                "Pechincha",
                "RIO DE JANEIRO",
                "RJ", "APTO 206", "ESTRADA CAMPO DA AREA", "84", "MARCELO DA SILVA RODRIGUES", estado
        );

        this.userId = this.jdbcTemplate.queryForObject("select id from usuario where email = 'marsilvarodrigues@gmail.com'", Long.class);
    }

    private void prepare() {

        jdbcTemplate.query("select id , residencial_id , celular_id from usuario where email in ( 'marsilvarodrigues@gmail.com' , 'lessarj@hotmail.com' )", new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

                final Long userId = rs.getLong("id");
                final Long celularId = rs.getLong("celular_id");
                final Long residencialId = rs.getLong("residencial_id");

                TestUsuarioController.this.jdbcTemplate.update("delete from usuario where id = ?", userId);
                TestUsuarioController.this.jdbcTemplate.update("delete from telefone where id in (?,?)", celularId, residencialId);

                return null;
            }

            ;
        });

    }

    @Test
    public void testFormulario() {

        controller.formulario();
        assertNotNull(result.included(Constante.LISTA_ESTADOS));
    }

    @Test
    public void testFormularioInicializeTodosOsCamposMarcadosComoEntidde() {
        controller.formulario();
        Usuario usuario = result.included(Constante.OBJECT);
        assertNotNull(usuario.getCelular());
        assertNotNull(usuario.getResidencial());
    }


    @Test
    public void inserir() {

        final Usuario usuario = createUsuario();

        context.checking(new Expectations() {{
            oneOf(email).from(with(aNonNull(String.class)));
            will(returnValue(email));

            oneOf(email).to(with(aNonNull(String.class)));
            will(returnValue(email));

            oneOf(email).subject(with(aNonNull(String.class)));
            will(returnValue(email));

            oneOf(email).template(with(aNonNull(String.class)), with(aNonNull(Map.class)));
            will(returnValue(email));

            oneOf(email).send();
        }});


        controller.salvar(usuario);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void alterar() {

        final Usuario usuario = createUsuario();
        usuario.setId(this.userId);

        controller.update(usuario);

        Long count = jdbcTemplate.queryForObject("select count(id) from usuario where email = ?", Long.class, usuario.getEmail());
        assertNotEquals(Long.valueOf(0L), count);
    }


    private Usuario createUsuario() {
        final Usuario usuario = new Usuario();
        usuario.setNomeCompleto("TESTE");

        final Telefone celular = new Telefone();
        celular.setDdd("021");
        celular.setNumero("12345678");

        usuario.setCelular(celular);
        usuario.setCpf("073.778.307-94");
        usuario.setDataNascimento(DateTime.now().toDate());
        usuario.setEmail("lessarj@hotmail.com");

        Estado estado = new Estado();
        estado.setId(331L);

        usuario.getEndereco().setEstado(estado);
        usuario.getEndereco().setBairro("TESTE");
        usuario.getEndereco().setCep("12345678");
        usuario.getEndereco().setCidade("RIO DE JANEIRO");
        usuario.getEndereco().setComplemento("TESTE");
        usuario.getEndereco().setLogradouro("TESTE");
        usuario.getEndereco().setNumero("TESTE");
        return usuario;
    }

    @Test
    public void carregarMeusDados() {
        final Authentication user = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("marsilvarodrigues@gmail.com", "12345678"));
        SecurityContextHolder.getContext().setAuthentication(user);

        controller.openUserProfile();

        assertNotNull(result.included(Constante.OBJECT));
    }

    @Test
    public void alterarOsPropriosDados() {

        final Authentication user = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("marsilvarodrigues@gmail.com", "12345678"));
        SecurityContextHolder.getContext().setAuthentication(user);

        final Usuario usuario = createUsuario();
        usuario.setId(this.userId);

        controller.updateUserProfile(usuario);

        Long count = jdbcTemplate.queryForObject("select count(id) from usuario where email = ?", Long.class, usuario.getEmail());
        assertNotEquals(Long.valueOf(0L), count);
    }


    @After
    public void after() {
        prepare();
    }
}
