package com.pmrodrigues.ellasa.models;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.RandomStringUtils;

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

	public String getCleanPassword() {
		return cleanPassword;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(unique = true, nullable = false)
	private String email;

	@NotNull
	@Column
	private String password;

	@Column
	private boolean bloqueado = true;

	@OneToMany(orphanRemoval = false, cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id")
	private final List<Telefone> telefones = new ArrayList<>();

	public List<Telefone> getTelefones() {
		return this.telefones;
	}

	public void adicionar(final Telefone telefone) {
		this.telefones.add(telefone);
	}

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

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public Long getId() {
		return id;
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
