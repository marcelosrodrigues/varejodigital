package com.pmrodrigues.ellasa.models;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.validator.constraints.NotBlank;

import com.pmrodrigues.ellasa.utilities.MD5;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
		@NamedQuery(name = "Usuario.All", query = "FROM Usuario"),
		@NamedQuery(name = "Usuario.FindByEmail", query = "FROM Usuario WHERE email = :email")})
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	private final String cleanPassword = RandomStringUtils
			.randomAlphanumeric(16);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "E-mail é obrigatório")
	@Column(unique = true, nullable = false)
	private String email;

	@Column
	private String password;

	@Column
	private boolean bloqueado = true;

	@OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
	private Celular celular;

	@OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
	private Residencial residencial;

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void desbloquear() {
		this.bloqueado = false;
	}

	public void bloquear() {
		this.bloqueado = true;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public Long getId() {
		return id;
	}

	public String getCleanPassword() {
		return cleanPassword;
	}

	public Celular getCelular() {
		return celular;
	}

	public void setCelular(final Celular celular) {
		this.celular = celular;
	}

	public Residencial getResidencial() {
		return residencial;
	}

	public void setResidencial(final Residencial residencial) {
		this.residencial = residencial;
	}

	@PrePersist
	public void preInsert() {
		try {
			this.password = MD5.encrypt(this.cleanPassword);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(
					"Erro para encriptar a senha do usuario, não permitindo assim o salvamento",
					e);
		}

	}
}
