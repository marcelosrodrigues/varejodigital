package com.pmrodrigues.ellasa.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.caelum.stella.bean.validation.CPF;

@Entity
@Table
public class FranqueadoPessoaFisica extends Franqueado {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(nullable = false)
	private String nomeCompleto;

	@Column(unique = true, nullable = false)
	@CPF(formatted = true)
	private String cpf;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataNascimento;

	public void setNomeCompleto(final String nome) {
		this.nomeCompleto = nome;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setCPF(final String cpf) {
		this.cpf = cpf;
	}

	public void setDataNascimento(final Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCPF() {
		return this.cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	@Override
	public String getNome() {
		return this.nomeCompleto;
	}

	@Override
	public String getDocumento() {
		return this.cpf;
	}

}
