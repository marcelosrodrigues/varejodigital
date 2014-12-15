package com.pmrodrigues.ellasa.models;

import static com.pmrodrigues.ellasa.Constante.FRANQUEADO;
import static com.pmrodrigues.ellasa.Constante.QUANTIDADE_MAXIMA_DE_FRANQUEADOS;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

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

	@Column(insertable = true, updatable = false, nullable = false, unique = true, columnDefinition = "CHAR(5)")
	private String codigo;

	@ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
	@JoinColumn(name = "franqueado_id")
	private Franqueado indicadoPor;

	@ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
	@JoinColumn(name = "pai_id")
	private Franqueado abaixoDe;

	@OneToMany()
	@JoinColumn(name = "pai_id", referencedColumnName = "id")
	private final Set<Franqueado> rede = new HashSet<>();

	public Franqueado() {
		super();
	}

	public void adicionar(final Franqueado franqueado) {
		if (rede.size() < QUANTIDADE_MAXIMA_DE_FRANQUEADOS) {
			rede.add(franqueado);
			franqueado.abaixoDe = this;
		} else {
			Franqueado membro = this.getRedeComMenosMembro();
			membro.adicionar(franqueado);
		}
		franqueado.indicadoPor = this;
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
		this.codigo = RandomStringUtils.randomAlphanumeric(5).toLowerCase();
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