package com.pmrodrigues.ellasa.models;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
public class Contrato implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; //NOPMD

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private final Date inicio = DateTime.now().toDate(); //NOPMD

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date termino; //NOPMD

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "franqueado_id")
	private Usuario franqueado;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "contrato_id")
	private TipoFranquia tipoContrato;

	public void setFranqueado(final Usuario franqueado) {
		this.franqueado = franqueado;
	}

	public Usuario getFranqueado() {
		return this.franqueado;
	}

	public void setTipoFranquia(final TipoFranquia tipo) {
		this.tipoContrato = tipo;
	}

	public TipoFranquia getTipoFranquia() {
		return this.tipoContrato;
	}

	public Contrato(final Usuario franqueado, final TipoFranquia tipo) {
		this();
		this.franqueado = franqueado;
		this.tipoContrato = tipo;
	}

    public Contrato() {
		//NOPMD
	}

}
