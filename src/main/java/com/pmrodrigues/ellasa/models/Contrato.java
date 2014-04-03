package com.pmrodrigues.ellasa.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class Contrato implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date inicio;

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date termino;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "franqueado_id")
	private Franqueado franqueado;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "contrato_id")
	private TipoFranquia tipoContrato;

	public void setFranqueado(final Franqueado franqueado) {
		this.franqueado = franqueado;
	}

	public Franqueado getFranqueado() {
		return this.franqueado;
	}

}
