package com.pmrodrigues.ellasa.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Marceloo on 17/11/2014.
 */
@Entity
@Table(name = "vendas")
public class Venda implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="franqueado_id")
    private Franqueado franqueado;

    @ManyToOne(optional = false)
    @JoinColumn(name="pedido_id")
    private Pedido pedido;

    public Venda(final Franqueado franqueado) {
        this();
        this.franqueado = franqueado;
    }

    public Venda() {}

    public void associar(Pedido pedido) {
        this.pedido = pedido;
    }
}
