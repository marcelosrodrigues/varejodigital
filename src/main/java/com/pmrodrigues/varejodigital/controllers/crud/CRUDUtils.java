package com.pmrodrigues.varejodigital.controllers.crud;

import com.pmrodrigues.varejodigital.annotations.After;
import com.pmrodrigues.varejodigital.annotations.Before;
import com.pmrodrigues.varejodigital.annotations.Insert;
import com.pmrodrigues.varejodigital.annotations.Update;
import com.pmrodrigues.varejodigital.exceptions.ErroNaoDocumentoException;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by Marceloo on 28/03/2015.
 */
class CRUDUtils<E> {

    private static final Logger logging = Logger.getLogger(CRUDUtils.class);
    private final AbstractCRUDController<E> controller;

    public CRUDUtils(final AbstractCRUDController controller) {
        this.controller = controller;
    }

    public boolean doUpdate(final E object) {
        try {
            return invoke(Update.class, object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ErroNaoDocumentoException(e);
        }
    }

    public boolean doInsert(final E object) {
        try {
            return invoke(Insert.class, object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ErroNaoDocumentoException(e);
        }

    }

    private Method getByAnnotation(final Class annotation) {

        final List<Method> metodos = Arrays.asList(controller.getClass().getMethods());
        for (final Method metodo : metodos) {
            if (metodo.isAnnotationPresent(annotation)) {
                logging.debug(format("Delegado a execução para o método da classe filha %s", metodo));
                return metodo;
            }
        }
        return null;

    }

    public void postExecute(final E object) {
        try {

            invoke(After.class, object);

        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ErroNaoDocumentoException(e);
        }
    }

    public Long getId(final E object) {
        try {
            final Field id = object.getClass().getDeclaredField("id");
            id.setAccessible(true);
            return (Long) id.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ErroNaoDocumentoException(e);
        }
    }

    public void preExecute() {

        try {
            invoke(Before.class);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ErroNaoDocumentoException(e);
        }

    }

    private boolean invoke(final Class annotation, final Object... params)
            throws IllegalAccessException, InvocationTargetException {
        final Method metodo = this.getByAnnotation(annotation);
        if (metodo != null) {
            metodo.invoke(controller, params);
            return true;
        }
        return false;
    }

}
