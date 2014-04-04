package com.pmrodrigues.ellasa.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.pmrodrigues.ellasa.pagamentos.entity.Transaction.PaymentMethod;

@Entity
@Table
@NamedQueries({@NamedQuery(name = "MeioPagamento.All", query = "FROM MeioPagamento ORDER BY id ASC")})
public class MeioPagamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String descricao;

	@Enumerated(EnumType.ORDINAL)
	private PaymentMethod method;

	public void setTipo(final PaymentMethod method) {
		this.method = method;
	}

	public PaymentMethod getTipo() {
		return method;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(final String descricao) {
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public boolean eCartao() {
		return (method == PaymentMethod.CARTAO_AMEX
				|| method == PaymentMethod.CARTAO_DINERS
				|| method == PaymentMethod.CARTAO_ELO
				|| method == PaymentMethod.CARTAO_MASTER || method == PaymentMethod.CARTAO_VISA);
	}

}
