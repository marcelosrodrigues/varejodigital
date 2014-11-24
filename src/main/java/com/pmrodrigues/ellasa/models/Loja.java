package com.pmrodrigues.ellasa.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Marceloo on 13/10/2014.
 */
@Entity
@Table(schema = "allinshopp" , name = "ps_shop")
public class Loja implements Serializable{

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(name = "id_shop")
     private Long id;

     @Column(name = "name")
     private String nome;

    public Long getId() {
        return id;
    }
}
