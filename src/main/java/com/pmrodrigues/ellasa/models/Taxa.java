package com.pmrodrigues.ellasa.models;

import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Marceloo on 13/10/2014.
 */
@Entity
@Table(name = "taxa")
@NamedQueries({
        @NamedQuery(name = "Taxa.All" , query = "select t from Taxa t")
})
public class Taxa implements Serializable{

    @Id
    private Long id;

    @Column
    private String nome;

    @Column
    private BigDecimal valor;

    public BigDecimal getValor() {
        return valor;
    }

    public String getNome() {
        return nome;
    }
}
