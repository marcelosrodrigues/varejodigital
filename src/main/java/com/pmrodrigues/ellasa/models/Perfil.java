package com.pmrodrigues.ellasa.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Marceloo on 18/12/2014.
 */

@Table(name = "perfil" )
@Entity
public class Perfil implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome" , unique = true)
    private String nome;


}
