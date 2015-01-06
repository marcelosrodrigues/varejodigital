package com.pmrodrigues.ellasa.models;

import br.com.caelum.stella.bean.validation.CPF;
import com.pmrodrigues.ellasa.utilities.MD5;
import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
		@NamedQuery(name = "Usuario.All", query = "FROM Usuario"),
		@NamedQuery(name = "Usuario.FindByEmail", query = "SELECT u FROM Usuario u inner join fetch u.endereco.estado left join fetch u.celular left join fetch u.residencial WHERE email = :email")})
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	private final String cleanPassword = RandomStringUtils
			.randomAlphanumeric(10);

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
	private Telefone celular;

	@OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
	private Telefone residencial;

    @Embedded
    private final Endereco endereco = new Endereco();

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nomeCompleto;

    @NotBlank(message = "CPF é obrigatório")
    @Column(unique = true, nullable = false)
    @CPF(formatted = true, message = "CPF inválido")
    private String cpf;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataNascimento;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "membros" , joinColumns = @JoinColumn(name = "usuario_id"),
                                  inverseJoinColumns = @JoinColumn(name = "perfil_id"),
                                  uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id","perfil_id"}))
    private Set<Perfil> perfis = new HashSet<>();

    public void setNomeCompleto(final String nome) {
        this.nomeCompleto = nome;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setCpf(final String cpf) {
        this.cpf = cpf;
    }

    public void setDataNascimento(final Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return this.cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

	public boolean isBloqueado() {
		return bloqueado;
	}

    public void setBloqueado( boolean bloqueado ){
        this.bloqueado = bloqueado;
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

    public void setId( final Long id ){
        this.id = id;
    }

	public String getCleanPassword() {
		return cleanPassword;
	}

	public Telefone getCelular() {
		return celular;
	}

	public void setCelular(final Telefone celular) {
		this.celular = celular;
	}

	public Telefone getResidencial() {
		return residencial;
	}

	public void setResidencial(final Telefone residencial) {
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
