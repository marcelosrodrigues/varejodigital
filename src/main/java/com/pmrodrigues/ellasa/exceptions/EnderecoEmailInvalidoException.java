package com.pmrodrigues.ellasa.exceptions;

public class EnderecoEmailInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EnderecoEmailInvalidoException(final String message, Throwable e) {
		super(message, e);
	}

}
