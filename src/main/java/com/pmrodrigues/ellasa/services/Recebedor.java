package com.pmrodrigues.ellasa.services;

public class Recebedor {

    private final String API_KEY;

    private final String EMAIL;

	public Recebedor(final String email, final String key) {
		super();
		this.EMAIL = email;
		this.API_KEY = key;
	}

	public String getApiKey() {
		return API_KEY;
	}

	public String getEmail() {
		return EMAIL;
	}

}
