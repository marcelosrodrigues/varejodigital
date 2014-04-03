package com.pmrodrigues.ellasa.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.caelum.stella.bean.validation.CPF;

@Entity
@Table
public class OrdemPagamentoCartaoCredito extends OrdemPagamento {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String numero;

	@Column(nullable = false)
	private String codigosegura;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataExpiracao;

	@Column(nullable = false)
	private String portador;

	@CPF
	@Column(nullable = false)
	private String cpf;

	@Column(nullable = false)
	private String telefone;

	public void setNumero(final String numero) {
		this.numero = numero;
	}

	public String getNumero() {
		return numero;
	}

	public void setCodigoSeguranca(final String codigoseguranca) {
		this.codigosegura = codigoseguranca;
	}

	public String getCodigoSeguranca() {
		return codigosegura;
	}

	public void setDataExpiracao(final Date dataexpiracao) {
		this.dataExpiracao = dataexpiracao;
	}

	public Date getDataExpiracao() {
		return dataExpiracao;
	}

	public void setPortador(final String portador) {
		this.portador = portador;
	}

	public void setCPF(final String cpf) {
		this.cpf = cpf;
	}

	public void setTelefone(final String telefone) {
		this.telefone = telefone;
	}

	public String getCPF() {
		return cpf;
	}

	public String getPortador() {
		return portador;
	}

	public String getTelefone() {
		return telefone;
	}
}
