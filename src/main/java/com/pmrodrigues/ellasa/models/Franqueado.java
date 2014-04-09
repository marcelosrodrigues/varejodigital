package com.pmrodrigues.ellasa.models;

import static com.pmrodrigues.ellasa.Constante.QUANTIDADE_MAXIMA_DE_FRANQUEADOS;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
		@NamedQuery(name = "Franqueado.FindByCodigo", query = "FROM Franqueado WHERE codigo =:codigo"),
		@NamedQuery(name = "Franqueado.All", query = "FROM Franqueado WHERE indicadoPor IS NULL order by nomeCompleto ASC")})
public abstract class Franqueado extends Usuario {

	private static final long serialVersionUID = 1L;

	@Column(insertable = true, updatable = false, nullable = false, unique = true, columnDefinition = "CHAR(10)")
	private String codigo;

	@Embedded
	private final Endereco endereco = new Endereco();

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "franqueado_id")
	private Franqueado indicadoPor;

	@OneToMany
	@JoinColumn(name = "franqueado_id", referencedColumnName = "id")
	private final Set<Franqueado> rede = new HashSet<>();

	public Franqueado() {
		super();
	}

	public void adicionar(final Franqueado franqueado) {
		if (rede.size() < QUANTIDADE_MAXIMA_DE_FRANQUEADOS) {
			rede.add(franqueado);
			franqueado.indicadoPor = this;
		} else {
	
			Franqueado membro = this.getRedeComMenosMembro();
			membro.adicionar(franqueado);
	
		}
	}

	private Franqueado getRedeComMenosMembro() {
	
		Franqueado selected = null;
	
		for (final Franqueado franqueado : this.rede) {
			if (selected == null
					|| selected.getQuantidadeMembros() > franqueado
							.getQuantidadeMembros()) {
				selected = franqueado;
			}
		}
	
		return selected;
	}

	public int getQuantidadeMembros() {
	
		int quantidade = this.rede.size();
	
		for (final Franqueado franqueado : this.rede) {
			quantidade += franqueado.getQuantidadeMembros();
		}
	
		return quantidade;
	}

	@Override
	@PrePersist
	public void preInsert() {
		super.preInsert();
		this.codigo = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
	}

	public Endereco getEndereco() {
		return this.endereco;
	}

	public String getCodigo() {
		return codigo;
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

	public Franqueado getIndicadoPor() {
		return indicadoPor;
	}

	public void setIndicadoPor(Franqueado indicadoPor) {
		this.indicadoPor = indicadoPor;
	}

	public Set<Franqueado> getRede() {
		return this.rede;
	}

	public abstract String getNome();

	public abstract String getDocumento();

}