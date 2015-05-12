package com.pmrodrigues.varejodigital.models;

import com.pmrodrigues.varejodigital.enumarations.Tipo;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "endereco")
public class EnderecoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //NOPMD

    @ManyToOne(optional = false, cascade = CascadeType.ALL, targetEntity = Cliente.class)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente; //NOPMD

    @Column
    private String logradouro;

    @Column
    private String bairro;

    @Column
    private String cep;

    @Column
    private String cidade;

    @Column
    private String telefone;

    @Column
    private String celular;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estado_id")
    private Estado estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dataCriacaco")
    private Date dataCriacao = DateTime.now().toDate(); //NOPMD

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date dataAlteracao = DateTime.now().toDate(); //NOPMD

    @Enumerated
    private Tipo tipo = Tipo.RESIDENCIAL; //NOPMD


    @PrePersist
    public void onInsert() {
        dataCriacao = DateTime.now().toDate();
        dataAlteracao = DateTime.now().toDate();
    }

    @PreUpdate
    public void onUpdate() {
        dataAlteracao = DateTime.now().toDate();
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(final String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(final String bairro) {
        this.bairro = bairro;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(final Estado estado) {
        this.estado = estado;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(final String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(final String CEP) {
        this.cep = CEP;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(final String celular) {
        this.celular = celular;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(final String telefone) {
        this.telefone = telefone;
    }

    public void setCliente(final Cliente cliente) {
        this.cliente = cliente;
    }
}
