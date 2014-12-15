package com.pmrodrigues.ellasa.controllers;

import br.com.caelum.vraptor.*;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.validator.ValidationMessage;
import com.pmrodrigues.ellasa.Constante;
import com.pmrodrigues.ellasa.annotations.*;
import com.pmrodrigues.ellasa.controllers.crud.AbstractCRUDController;
import com.pmrodrigues.ellasa.exceptions.UniqueException;
import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.models.Usuario;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;
import com.pmrodrigues.ellasa.repositories.Repository;
import com.pmrodrigues.ellasa.repositories.UsuarioRepository;
import com.pmrodrigues.ellasa.services.EmailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

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

    private final EstadoRepository estadoRepository;

    private final EmailService email;

    private static final Logger logging = Logger.getLogger(UsuarioController.class);

    protected UsuarioController(UsuarioRepository repository, EstadoRepository estadoRepository, EmailService email , Result result, Validator validator) {
        super(repository, result, validator);
        this.estadoRepository = estadoRepository;
        this.email = email;
    }

    @Before
    public void carregar() {

        logging.trace("carregando os estados para montagem da tela");
        final List<Estado> estados = estadoRepository.list();
        super.getResult().include(Constante.LISTA_ESTADOS,estados);
    }

    @Update
    public void update(final Usuario object) {

        logging.debug(format("alterando o usuario %s",object));

        final Usuario existed = this.getRepository().findById(object.getId());
        existed.setNomeCompleto(object.getNomeCompleto());
        existed.setBloqueado(object.isBloqueado());
        existed.setCpf(object.getCpf());
        existed.setDataNascimento(object.getDataNascimento());
        existed.setEmail(object.getEmail());
        existed.setResidencial(object.getResidencial());
        existed.getEndereco().setBairro(object.getEndereco().getBairro());
        existed.getEndereco().setCep(object.getEndereco().getCep());
        existed.getEndereco().setCidade(object.getEndereco().getCidade());
        existed.getEndereco().setComplemento(object.getEndereco().getComplemento());
        existed.getEndereco().setEstado(object.getEndereco().getEstado());
        existed.getEndereco().setLogradouro(object.getEndereco().getLogradouro());
        existed.getEndereco().setNumero(object.getEndereco().getNumero());
        this.getRepository().set(existed);

        logging.debug(format("Usuario %s alterado com sucesso",object));
    }


    @Tiles(definition = "usuario/meus-dados-template")
    @Path("/meus-dados.do")
    @Get
    public void openUserProfile() {

        this.carregar();

        final Authentication userAuthenticated = SecurityContextHolder.getContext().getAuthentication();
        final User user = (User) userAuthenticated.getPrincipal();
        final Usuario object = ((UsuarioRepository)this.getRepository()).findByEmail(user.getUsername());

        this.getResult().include(Constante.OBJECT,object);
    }

    @Tiles(definition = "usuario/meus-dados-template")
    @Path("/meus-dados.do")
    @Post
    public void updateUserProfile(final Usuario object){

        try {

            final Authentication userAuthenticated = SecurityContextHolder.getContext().getAuthentication();
            final User user = (User) userAuthenticated.getPrincipal();
            final Usuario existed = ((UsuarioRepository)this.getRepository()).findByEmail(user.getUsername());

            existed.setNomeCompleto(object.getNomeCompleto());
            existed.setCpf(object.getCpf());
            existed.setDataNascimento(object.getDataNascimento());
            existed.setEmail(object.getEmail());
            existed.setResidencial(object.getResidencial());
            existed.getEndereco().setBairro(object.getEndereco().getBairro());
            existed.getEndereco().setCep(object.getEndereco().getCep());
            existed.getEndereco().setCidade(object.getEndereco().getCidade());
            existed.getEndereco().setComplemento(object.getEndereco().getComplemento());
            existed.getEndereco().setEstado(object.getEndereco().getEstado());
            existed.getEndereco().setLogradouro(object.getEndereco().getLogradouro());
            existed.getEndereco().setNumero(object.getEndereco().getNumero());
            this.getRepository().set(existed);

            this.getResult().include(Constante.SUCESSO, "Seus dados foram alterados com sucesso");
        } catch (UniqueException e) {
            super.getValidator().add(new ValidationMessage(e.getMessage(),e.getMessage()));
            super.getValidator().onErrorForwardTo(this.getClass()).openUserProfile();
        }
    }

    @After
    public void enviarEmailComSenha(final Usuario usuario) {

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("usuario",usuario);

        email.from("sac@catalogodigitalellasa.com.br")
                .to(usuario.getEmail())
                .subject("Seja bem-vindo a Ella S/A")
                .template("/templates/novosusuarios.vm", parameters).send();

    }
}
