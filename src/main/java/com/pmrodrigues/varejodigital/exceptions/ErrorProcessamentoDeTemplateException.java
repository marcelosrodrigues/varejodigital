package com.pmrodrigues.varejodigital.exceptions;


public class ErrorProcessamentoDeTemplateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ErrorProcessamentoDeTemplateException(final String message,
                                                 Exception e) {
        super(message, e);
    }

}
