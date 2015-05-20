package com.pmrodrigues.varejodigital.models;

import br.com.caelum.stella.bean.validation.CPF;
import com.pmrodrigues.varejodigital.Constante;
import com.pmrodrigues.varejodigital.utilities.MD5;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@DynamicUpdate(true)
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
        @NamedQuery(name = "Usuario.All", query = "FROM Usuario"),
        @NamedQuery(name = "Usuario.FindByEmail", query = "SELECT u FROM Usuario u inner join fetch u.endereco.estado left join fetch u.celular left join fetch u.residencial WHERE email = :email")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    private final String cleanPassword = RandomStringUtils
            .randomAlphanumeric(10);

    @Embedded
    private final Endereco endereco = new Endereco();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "membros", joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "perfil_id"}))
    private final Set<Perfil> perfis = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "lojistas", joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "loja_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "loja_id"}))
    private final Set<Loja> lojas = new HashSet<>();

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

    @Column
    private Long tentativas = 0L;

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(final String nome) {
        this.nomeCompleto = nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(final String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(final Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void desbloquear() {
        this.tentativas = 0L;
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

    public void setId(final Long id) {
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
        this.password = MD5.encrypt(this.cleanPassword);
    }

    public void incrementarTentativasFalhas() {
        tentativas++;
        if (tentativas == Constante.NUMERO_MAXIMO_TENTATIVAS_FALHAS) {
            bloquear();
        }
    }

    public Set<Perfil> getRoles() {
        return this.perfis;
    }

    public Set<Loja> getLojas() {
        return this.lojas;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Usuario) {
            final Usuario other = (Usuario) obj;
            if (this.id == null || other.id == null) {
                return this.email.equalsIgnoreCase(other.email);
            } else {
                return this.id.equals(other.id);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (this.id == null && this.email == null && this.cpf == null) {
            return 0;
        } else {
            final HashCodeBuilder hsc = new HashCodeBuilder(7, 43);
            return hsc.append(this.id)
                    .append(this.email)
                    .append(this.cpf)
                    .toHashCode();
        }
    }

    public void adicionar(final Collection<Loja> lojas) {
        this.lojas.addAll(lojas);
    }

    public void remover(final Collection<Loja> lojas) {
        this.lojas.removeAll(lojas);
    }
}
