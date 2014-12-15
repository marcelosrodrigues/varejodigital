package com.pmrodrigues.ellasa.controllers.crud;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import com.pmrodrigues.ellasa.Constante;
import com.pmrodrigues.ellasa.annotations.After;
import com.pmrodrigues.ellasa.annotations.Before;
import com.pmrodrigues.ellasa.annotations.Insert;
import com.pmrodrigues.ellasa.annotations.Update;
import com.pmrodrigues.ellasa.controllers.IndexController;
import com.pmrodrigues.ellasa.exceptions.UniqueException;
import com.pmrodrigues.ellasa.repositories.Repository;
import com.pmrodrigues.ellasa.repositories.ResultList;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by Marceloo on 08/12/2014.
 */
public abstract class AbstractCRUDController<E> {

    private static final Logger logging = Logger.getLogger(AbstractCRUDController.class);

    private final Repository<E> repository;
    private final Result result;
    private final Validator validator;
    private final Class<E> persistentClass; //NOPMD

    protected AbstractCRUDController(final Repository<E> repository, final Result result, final Validator validator) {
        this.repository = repository;
        this.result = result;
        this.validator = validator;
        final ParameterizedType type = (ParameterizedType) this.getClass()
                .getGenericSuperclass();
        this.persistentClass = (Class<E>) type.getActualTypeArguments()[0];
    }

    @Post
    public void salvar(final E object){

        validator.validate(object);
        final Long idValue = getId(object);
        validator.onErrorForwardTo(this.getClass()).formulario();

        logging.debug(format("iniciando a operação de inclusão de %s",object));

        try {
            if( idValue == null || idValue == 0L ) {

                if( !doInsert(object) ) {
                    repository.add(object);
                    postExecute(object);
                }
                result.include(Constante.SUCESSO,format("%s adicionada com sucesso",persistentClass.getSimpleName()));
            } else {
                if( !doUpdate(object) ) {
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

    private boolean doUpdate(final E object) {
        try {
            List<Method> metodos = Arrays.asList(this.getClass().getMethods());
            for( final Method metodo : metodos ){
                if( metodo.isAnnotationPresent(Update.class)) {
                    logging.debug(format("Delegado a execução para o método da classe filha %s",metodo));
                    metodo.invoke(this,object);
                    return true;
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private boolean doInsert(final E object) {
        try {
            List<Method> metodos = Arrays.asList(this.getClass().getMethods());
            for( final Method metodo : metodos ){
                if( metodo.isAnnotationPresent(Insert.class)) {
                    logging.debug(format("Delegado a execução para o método da classe filha %s",metodo));
                    metodo.invoke(this,object);
                    return true;
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private void postExecute(final E object) {
        try {
            List<Method> metodos = Arrays.asList(this.getClass().getMethods());
            for( final Method metodo : metodos ){
                if( metodo.isAnnotationPresent(After.class)) {
                    logging.debug(format("Delegado a execução para o método da classe filha %s",metodo));
                    metodo.invoke(this,object);
                    break;
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Long getId(final E object)  {
        try {
            Field id = object.getClass().getDeclaredField("id");
            id.setAccessible(true);
            return (Long) id.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Get
    public void index() {
        preExecute();
        final ResultList<E> resultlist = repository.search(null);
        result.include(Constante.RESULT_LIST , resultlist);
    }

    private void preExecute() {

        try {
            List<Method> metodos = Arrays.asList(this.getClass().getMethods());
            for( final Method metodo : metodos ){
                if( metodo.isAnnotationPresent(Before.class)) {
                    logging.debug(format("Delegado a execução para o método da classe filha %s",metodo));
                    metodo.invoke(this);
                    break;
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    public void search(Integer page , final E object ) {

        preExecute();

        if( page == null ){
            page = 0;
        }

        final ResultList<E> resultlist = repository.search(object,page);
        result.include(Constante.RESULT_LIST , resultlist);


    }


    @Get
    public void formulario() {
        try {
            preExecute();
            final E e = this.persistentClass.newInstance();
            result.include(Constante.OBJECT,e);
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Get
    public void show(final Long id){
        preExecute();
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
}
