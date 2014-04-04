package com.pmrodrigues.ellasa.models;

import static com.pmrodrigues.ellasa.Constante.QUANTIDADE_MAXIMA_DE_FRANQUEADOS;
import static java.lang.String.format;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import br.com.caelum.stella.bean.validation.CPF;

import com.pmrodrigues.ellasa.exceptions.EstouroTamanhoDeRedeException;

@Entity
@Table
@NamedQueries({
		@NamedQuery(name = "Franqueado.FindByCodigo", query = "FROM Franqueado WHERE codigo =:codigo"),
		@NamedQuery(name = "Franqueado.All", query = "FROM Franqueado WHERE indicadoPor IS NULL order by nomeCompleto ASC")})
public class Franqueado extends Usuario {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(nullable = false)
	private String nomeCompleto;

	@NotNull
	@Column(insertable = true, updatable = false, nullable = false, unique = true, columnDefinition = "CHAR(10)")
	private String codigo;

	@Column(unique = true, nullable = false)
	@CPF(formatted = true)
	private String cpf;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataNascimento;

	@Embedded
	private final Endereco endereco = new Endereco();

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "franqueado_id")
	private Franqueado indicadoPor;

	@OneToMany
	@JoinColumn(name = "franqueado_id", referencedColumnName = "id")
	private final Set<Franqueado> rede = new HashSet<>();


	public void adicionar(final Franqueado franqueado) {
		if (rede.size() < QUANTIDADE_MAXIMA_DE_FRANQUEADOS) {
			rede.add(franqueado);
			franqueado.indicadoPor = this;
		} else {
			throw new EstouroTamanhoDeRedeException(
					format("Impossível adicionar um novo franqueado, você já atingiu ao limite de %s franqueados na sua rede",
							QUANTIDADE_MAXIMA_DE_FRANQUEADOS));
		}
	}

	@Override
	@PrePersist
	public void preInsert() {
		super.preInsert();
		this.codigo = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
	}

	public void setNomeCompleto(final String nome) {
		this.nomeCompleto = nome;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public Endereco getEndereco() {
		return this.endereco;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCPF(final String cpf) {
		this.cpf = cpf;
	}

	@Override
	public boolean equals(final Object obj) {

		boolean isEquals = false;
		if (obj instanceof Franqueado) {			
			final Franqueado other = (Franqueado) obj;
			isEquals = (this.codigo != null && other.codigo != null && this.codigo
					.equalsIgnoreCase(other.codigo));
			
		}
		return isEquals;
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder hsh = new HashCodeBuilder();
		return hsh.append(this.codigo).toHashCode();
	}

	public void setDataNascimento(final Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCPF() {
		return this.cpf;
	}

}
