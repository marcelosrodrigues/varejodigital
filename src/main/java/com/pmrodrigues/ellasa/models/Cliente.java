package com.pmrodrigues.ellasa.models;

import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="ps_customer" , schema = "allinshopp")
@NamedQueries({@NamedQuery(name = "Cliente.All", query = "SELECT c FROM Cliente c inner join fetch c.endereco e inner join fetch e.estado ORDER BY c.id ASC")})
public class Cliente implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_customer")
	private Long id;
	
	@Column(name="firstname")
	private String primeiroNome;
	
	@Column(name="lastname")
	private String ultimoNome;
	
	@Column(name="email")
	private String email;
	
	@Temporal(TemporalType.DATE)
	@Column(name="birthday")
	private Date dataNascimento;

    @Column(name = "id_gender")
    private final Long tratamento = 3L;

    @Column(name = "passwd")
    private final String passwd = RandomStringUtils.randomAlphanumeric(10);
	
	@Column(name = "active")
	private final boolean active = true;
	
	@OneToOne(mappedBy="cliente" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
	@JoinColumn(name="id_customer")
	private EnderecoCliente endereco;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_add")
    private Date dataCriacaco = DateTime.now().toDate();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_upd")
    private Date dataAlteracao = DateTime.now().toDate();


    @PrePersist
    public void onInsert() {
        dataCriacaco = DateTime.now().toDate();
        dataAlteracao = DateTime.now().toDate();
        this.endereco.setCliente(this);

    }

    @PreUpdate
    public void onUpdate() {
        dataAlteracao = DateTime.now().toDate();
        this.endereco.setCliente(this);
    }

    public Long getId() {
        return id;
    }

    public EnderecoCliente getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoCliente endereco) {
        this.endereco = endereco;
        this.endereco.setPrimeiroNome(this.primeiroNome);
        this.endereco.setUltimoNome(this.ultimoNome);
        this.endereco.setCliente(this);
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return primeiroNome + " " + ultimoNome;
    }


    public void setPrimeiroNome(final String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public void setUltimoNome(final String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setDataNascimento(final Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public String getUltimoNome() {
        return ultimoNome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }
}
