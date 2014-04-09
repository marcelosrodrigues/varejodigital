package com.pmrodrigues.ellasa.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Logradouro é obrigatório")
	@Column(nullable = false)
	private String logradouro;

	@NotBlank(message = "Bairro é obrigatório")
	@Column(nullable = false)
	private String bairro;

	@NotBlank(message = "CEP é obrigatório")
	@Column(nullable = false)
	private String cep;

	@NotBlank(message = "Número é obrigatório")
	@Column(nullable = false)
	private String numero;

	@NotBlank(message = "Cidade é obrigatório")
	@Column(nullable = false)
	private String cidade;

	@Column(nullable = true)
	private String complemento;

	@NotNull(message = "Estado é obrigatório")
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "uf")
	private Estado estado;

	public void setEstado(final Estado estado) {
		this.estado = estado;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Estado getEstado() {
		return estado;
	}
}
