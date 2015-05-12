package com.pmrodrigues.varejodigital.converters;

import br.com.caelum.vraptor.Converter;
import com.pmrodrigues.varejodigital.repositories.Repository;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import java.util.ResourceBundle;

import static java.lang.String.format;

public class AbstractTypeConverter<E> implements Converter<E> {

    private static final Logger logging = Logger
            .getLogger(AbstractTypeConverter.class);
    private final Repository<E> repository;

    public AbstractTypeConverter(final Repository<E> repository) {
        this.repository = repository;
    }

    @Override
    public E convert(final String value, final Class<? extends E> type,
                     final ResourceBundle bundle) {
        logging.debug(format("Convertenado para o tipo %s", type));

        E entity = null;

        if (!GenericValidator.isBlankOrNull(value)
                && GenericValidator.isLong(value)) {
            entity = repository.findById(Long.parseLong(value));
            logging.debug(format("Objeto convertido %s", entity));
        }

        return entity;
    }

}
