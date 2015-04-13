package com.pmrodrigues.ellasa.controllers.crud;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import com.pmrodrigues.ellasa.Constante;
import com.pmrodrigues.ellasa.exceptions.ErroNaoDocumentoException;
import com.pmrodrigues.ellasa.exceptions.UniqueException;
import com.pmrodrigues.ellasa.repositories.Repository;
import com.pmrodrigues.ellasa.repositories.ResultList;
import org.apache.log4j.Logger;

import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by Marceloo on 08/12/2014.
 */
public abstract class AbstractCRUDController<E> { //NOPMD

    private static final Logger logging = Logger.getLogger(AbstractCRUDController.class);

    private final Repository<E> repository;
    private final Result result;
    private final Validator validator;
    private final Class<E> persistentClass; //NOPMD
    private final CRUDUtils<E> crudutils;

    protected AbstractCRUDController(final Repository<E> repository, final Result result, final Validator validator) {
        this.repository = repository;
        this.result = result;
        this.validator = validator;
        final ParameterizedType type = (ParameterizedType) this.getClass()
                .getGenericSuperclass();
        this.persistentClass = (Class<E>) type.getActualTypeArguments()[0];
        this.crudutils = new CRUDUtils<>(this);
    }

    @Post
    public void salvar(final E object){

        validator.validate(object);
        final Long idValue = crudutils.getId(object);
        validator.onErrorForwardTo(this.getClass()).formulario();

        logging.debug(format("iniciando a operação de inclusão de %s",object));

        try {
            if( idValue == null || idValue == 0L ) {

                if (!crudutils.doInsert(object)) {
                    repository.add(object);
                }
                crudutils.postExecute(object);
                result.include(Constante.SUCESSO,format("%s adicionada com sucesso",persistentClass.getSimpleName()));
            } else {
                if (!crudutils.doUpdate(object)) {
                    repository.set(object);
                }
                result.include(Constante.SUCESSO, format("%s alterada com sucesso", persistentClass.getSimpleName()));
            }

            result.forwardTo(this.getClass()).index();
        } catch (UniqueException ex) {
            validator.add(new ValidationMessage(ex.getMessage(),ex.getMessage()));
            validator.onErrorForwardTo(this.getClass()).formulario();
        }
    }

    @Get
    public void index() {
        crudutils.preExecute();
        final ResultList<E> resultlist = repository.search(null);
        result.include(Constante.RESULT_LIST , resultlist);
    }

    public void search(Integer page , final E object ) {

        crudutils.preExecute();

        if( page == null ){
            page = 0;
        }

        final ResultList<E> resultlist = repository.search(object,page);
        result.include(Constante.RESULT_LIST , resultlist);


    }

    @Get
    public void formulario() {
        try {
            crudutils.preExecute();
            final E e = this.persistentClass.newInstance();
            final List<Field> fields = Arrays.asList(e.getClass().getDeclaredFields());
            for (final Field field : fields) {
                if( field.getType().isAnnotationPresent(Entity.class) ){
                    field.setAccessible(true);
                    final Object value = field.getType().newInstance();
                    field.set(e,value);
                }
            }

            result.include(Constante.OBJECT,e);
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new ErroNaoDocumentoException(ex);
        }
    }

    @Get
    public void show(final Long id){
        crudutils.preExecute();
        final E e = this.repository.findById(id);
        result.include(Constante.OBJECT,e);
    }

    public Result getResult() {
        return result;
    }

    public Repository<E> getRepository() {
        return repository;
    }

    public Validator getValidator() {
        return validator;
    }

    @Post
    public void delete(final E object) {
        logging.debug(format("excluindo o valor %s do banco de dados", object));

        repository.remove(object);

        logging.debug(format("%s excluido com sucesso", object));
        result.include(Constante.SUCESSO, format("%s excluído com sucesso", persistentClass.getSimpleName()));
        result.forwardTo(this.getClass()).index();
    }
}
