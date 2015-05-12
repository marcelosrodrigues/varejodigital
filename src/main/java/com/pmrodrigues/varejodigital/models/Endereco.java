package com.pmrodrigues.varejodigital.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Logradouro é obrigatório")
    @Column(nullable = false)
    private String logradouro;

    @NotBlank(message = "Bairro é obrigatório")
    @Column(nullable = false)
    private String bairro;

    @NotBlank(message = "CEP é obrigatório")
    @Column(nullable = false)
    private String cep;

    @NotBlank(message = "Número é obrigatório")
    @Column(nullable = false)
    private String numero;

    @NotBlank(message = "Cidade é obrigatório")
    @Column(nullable = false)
    private String cidade;

    @Column(nullable = true)
    private String complemento;

    @NotNull(message = "Estado é obrigatório")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_id")
    private Estado estado;

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(final String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(final String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(final String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(final String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(final String cidade) {
        this.cidade = cidade;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(final String complemento) {
        this.complemento = complemento;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(final Estado estado) {
        this.estado = estado;
    }
}
