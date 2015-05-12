package com.pmrodrigues.varejodigital.exceptions;

/**
 * Created by Marceloo on 08/04/2015.
 */
public class ErroNaoDocumentoException extends RuntimeException {
    public ErroNaoDocumentoException(final Throwable e) {
        super(e);
    }

    public ErroNaoDocumentoException(final String message) {
        super(message);
    }
}
