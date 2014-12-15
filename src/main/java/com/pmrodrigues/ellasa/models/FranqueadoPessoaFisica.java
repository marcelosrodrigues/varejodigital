package com.pmrodrigues.ellasa.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.com.caelum.stella.bean.validation.CPF;

@Entity
@Table
public class FranqueadoPessoaFisica extends Franqueado {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Nome é obrigatório")
	@Column(nullable = false)
	private String nomeCompleto;

	@NotBlank(message = "CPF é obrigatório")
	@Column(unique = true, nullable = false)
	@CPF(formatted = true, message = "CPF inválido")
	private String cpf;

	@NotNull(message = "Data de nascimento é obrigatória")
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataNascimento;

	public void setNomeCompleto(final String nome) {
		this.nomeCompleto = nome;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setCpf(final String cpf) {
		this.cpf = cpf;
	}

	public void setDataNascimento(final Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
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
