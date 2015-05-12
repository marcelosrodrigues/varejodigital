package com.pmrodrigues.varejodigital.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Telefone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String ddd;

    @Column(nullable = false)
    private String telefone;

    public String getDdd() {
        return ddd;
    }

    public void setDdd(final String ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return telefone;
    }

    public void setNumero(final String telefone) {
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

}
