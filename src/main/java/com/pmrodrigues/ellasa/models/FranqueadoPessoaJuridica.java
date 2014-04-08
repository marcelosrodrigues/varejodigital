package com.pmrodrigues.ellasa.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.caelum.stella.bean.validation.CNPJ;

@Table
@Entity
public class FranqueadoPessoaJuridica extends Franqueado {

	private static final long serialVersionUID = 1L;

	@CNPJ(formatted = true)
	private String cnpj;

	@NotNull
	@Column(nullable = true)
	private String razaoSocial;

	@NotNull
	@Column(nullable = true)
	private String nomeFantasia;

	public String getCNPJ() {
		return cnpj;
	}

	public void setCNPJ(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	@Override
	public String getNome() {
		return this.razaoSocial;
	}

	@Override
	public String getDocumento() {
		return this.cnpj;
	}
}

