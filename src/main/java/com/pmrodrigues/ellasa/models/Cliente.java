package com.pmrodrigues.ellasa.models;

import com.pmrodrigues.ellasa.enumarations.Genero;
import org.hibernate.annotations.Where;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "cliente")
@NamedQueries({@NamedQuery(name = "Cliente.All", query = "SELECT c FROM Cliente c inner join fetch c.endereco e inner join fetch e.estado ORDER BY c.id ASC")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String primeiroNome;

    @Column
    private String ultimoNome;

    @Column
    private String email;

    @Temporal(TemporalType.DATE)
    @Column
    private Date dataNascimento;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "cliente")
    @Where(clause = "tipo = 0")
    private EnderecoCliente endereco;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dataCriacaco")
    private Date dataCriacao = DateTime.now().toDate(); //NOPMD

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date dataAlteracao = DateTime.now().toDate(); //NOPMD

    @Enumerated(EnumType.ORDINAL)
    private Genero sexo;


    @PrePersist
    public void onInsert() {
        dataCriacao = DateTime.now().toDate();
        dataAlteracao = DateTime.now().toDate();
    }

    @PreUpdate
    public void onUpdate() {
        dataAlteracao = DateTime.now().toDate();
    }

    public Long getId() {
        return id;
    }

    public EnderecoCliente getEndereco() {
        return endereco;
    }

    public void setEndereco(final EnderecoCliente endereco) {
        this.endereco = endereco;
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

    public boolean isNovo() {
        return this.id == null || 0L == this.id;
    }

    public Genero getSexo() {
        return sexo;
    }

    public void setSexo(final Genero sexo) {
        this.sexo = sexo;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
