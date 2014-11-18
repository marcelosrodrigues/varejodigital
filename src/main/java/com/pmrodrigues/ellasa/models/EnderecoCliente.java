package com.pmrodrigues.ellasa.models;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ps_address" , schema = "allinshopp")
public class EnderecoCliente {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_address")
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="id_customer")
	private Cliente cliente;
	
	@Column(name = "alias")
	private final String alias = "ELLASA";
	
	@Column(name="firstname")
	private String primeiroNome;
	
	@Column(name="lastname")
	private String ultimoNome;
	
	@Column(name="address1")
	private String logradouro;
	
	@Column(name="address2")
	private String bairro;
	
	@Column(name="postcode")
	private String cep;
	
	@Column(name="city")
	private String cidade;
	
	@Column(name="phone")
	private String telefone;
	
	@Column(name="phone_mobile")
	private String celular;
	
	@Column(name="active")
	private boolean active = true;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="id_state")
	private Estado estado;

    @Column(name = "id_country")
    private Long pais = 58L;

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
    }

    @PreUpdate
    public void onUpdate() {
        dataAlteracao = DateTime.now().toDate();
    }

    public String getCidade() {
        return cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public Estado getEstado() {
        return estado;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getCep() {
        return cep;
    }

    public String getCelular() {
        return celular;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setPrimeiroNome(final String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public void setUltimoNome(final String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }

    public void setCliente(final Cliente cliente) {
        this.cliente = cliente;
        this.primeiroNome = cliente.getPrimeiroNome();
        this.ultimoNome = cliente.getUltimoNome();
    }

    public void setLogradouro(final String logradouro) {
        this.logradouro = logradouro;
    }

    public void setBairro(final String bairro) {
        this.bairro = bairro;
    }

    public void setCep(final String CEP) {
        this.cep = CEP;
    }

    public void setTelefone(final String telefone) {
        this.telefone = telefone;
    }

    public void setCelular(final String celular) {
        this.celular = celular;
    }

    public void setCidade(final String cidade) {
        this.cidade = cidade;
    }
}
