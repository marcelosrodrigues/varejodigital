package com.pmrodrigues.varejodigital.controllers;

import br.com.caelum.vraptor.*;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.caelum.vraptor.view.Results;
import com.pmrodrigues.varejodigital.Constante;
import com.pmrodrigues.varejodigital.annotations.*;
import com.pmrodrigues.varejodigital.controllers.crud.AbstractCRUDController;
import com.pmrodrigues.varejodigital.exceptions.ErroNaoDocumentoException;
import com.pmrodrigues.varejodigital.exceptions.UniqueException;
import com.pmrodrigues.varejodigital.models.Estado;
import com.pmrodrigues.varejodigital.models.Usuario;
import com.pmrodrigues.varejodigital.repositories.EstadoRepository;
import com.pmrodrigues.varejodigital.repositories.UsuarioRepository;
import com.pmrodrigues.varejodigital.services.EmailService;
import com.pmrodrigues.varejodigital.sessionscope.Lojas;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

/**
 * Created by Marceloo on 11/12/2014.
 */
@Component
@CRUD
public class UsuarioController extends AbstractCRUDController<Usuario> {

    private static final Logger logging = Logger.getLogger(UsuarioController.class);
    private final EstadoRepository estadoRepository;
    private final EmailService email;
    private final Lojas lojas;

    public UsuarioController(final UsuarioRepository repository, final EstadoRepository estadoRepository,
                             final Lojas lojas, final EmailService email, final Result result, final Validator validator) {
        super(repository, result, validator);
        this.estadoRepository = estadoRepository;
        this.email = email;
        this.lojas = lojas;
    }

    @Before
    public void carregar() {

        logging.trace("carregando os estados para montagem da tela");
        final List<Estado> estados = estadoRepository.list();
        super.getResult().include(Constante.LISTA_ESTADOS, estados);
    }

    @Insert
    public void insert(final Usuario object) {

        object.adicionar(lojas.novos());
        lojas.apagar();

        this.getRepository().add(object);

    }

    @Update
    public void update(final Usuario object) {

        try {
            logging.debug(format("alterando o usuario %s", object));

            final Usuario existed = this.getRepository().findById(object.getId());

            update(object, existed);

            logging.debug(format("Usuario %s alterado com sucesso", object));
        } catch (IllegalAccessException | InvocationTargetException e) {
            logging.fatal("erro ao copiar os valores digitados para a entidade existente no banco de dados " +
                    e.getMessage(), e);
            throw new ErroNaoDocumentoException(e);
        }
    }

    private void update(final Usuario origem, final Usuario destino)
            throws IllegalAccessException, InvocationTargetException {
        BeanUtils.copyProperties(destino, origem);
        BeanUtils.copyProperties(destino.getEndereco(), origem.getEndereco());
        BeanUtils.copyProperties(destino.getCelular(), origem.getCelular());

        if (origem.getResidencial() != null && GenericValidator.isBlankOrNull(origem.getResidencial().getDdd())
                && GenericValidator.isBlankOrNull(origem.getResidencial().getNumero())) {
            destino.setResidencial(null);
        }
        destino.adicionar(lojas.novos());
        destino.remover(lojas.removidos());
        lojas.apagar();
        this.getRepository().set(destino);
    }


    @Tiles(definition = "usuario/meus-dados-template")
    @Path("/meus-dados.do")
    @Get
    public void openUserProfile() {

        this.carregar();

        final Authentication userAuthenticated = SecurityContextHolder.getContext().getAuthentication();
        final String username = (String) userAuthenticated.getPrincipal();
        final Usuario object = ((UsuarioRepository) this.getRepository()).findByEmail(username);

        this.getResult().include(Constante.OBJECT, object);
    }

    @Tiles(definition = "usuario/meus-dados-template")
    @Path("/meus-dados.do")
    @Post
    public void updateUserProfile(final Usuario object) {

        try {

            final Authentication userAuthenticated = SecurityContextHolder.getContext().getAuthentication();
            final String username = (String) userAuthenticated.getPrincipal();
            final Usuario existed = ((UsuarioRepository) this.getRepository()).findByEmail(username);

            update(object, existed);

            this.getResult().include(Constante.SUCESSO, "Seus dados foram alterados com sucesso");
        } catch (UniqueException e) {
            super.getValidator().add(new ValidationMessage(e.getMessage(), e.getMessage()));
            super.getValidator().onErrorForwardTo(this.getClass()).openUserProfile();
        } catch (InvocationTargetException | IllegalAccessException e) {
            logging.fatal("erro ao copiar os valores digitados para a entidade existente no banco de dados " + e.getMessage(), e);
            throw new ErroNaoDocumentoException(e);
        }
    }

    @After
    public void enviarEmailComSenha(final Usuario usuario) {

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("usuario", usuario);

        email.from("sac@catalogodigitalellasa.com.br")
                .to(usuario.getEmail())
                .subject("Seja bem-vindo a Ella S/A")
                .template("/templates/novosusuarios.vm", parameters).send();

    }

    @Get
    @Path("/usuarios/{nome}/list.json")
    public List<Usuario> pesquisarUsuario(final String nome) {
        final UsuarioRepository repository = (UsuarioRepository) this.getRepository();
        final List<Usuario> usuarios = repository.listByNomeOrEmail(nome);
        this.getResult().use(Results.json())
                .from(usuarios)
                .include("id", "nomeCompleto", "email")
                .exclude("password", "cleanPassword", "bloqueado", "celular", "residencial",
                        "cpf", "dataNascimento", "perfis", "tentativas")
                .serialize();

        return usuarios;
    }
}
