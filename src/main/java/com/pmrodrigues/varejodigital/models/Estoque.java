package com.pmrodrigues.varejodigital.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created by Marceloo on 14/05/2015.
 */
@XmlType(name = "EstoqueType" , namespace = "http://schema.varejodigital.projetandoo/1.0/")
@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class Estoque implements Serializable{

    @XmlElement(name = "minimo" , required = true)
    @Column(name="quantidade_min_estoque" , nullable = false)
    private Long minimo;

    @XmlElement(name = "disponivel" , required = true)
    @Column(name="quantidade_em_estoque", nullable = false)
    private Long disponivel;

    @XmlElement(name = "maximo" , required = true)
    @Column(name="quantidade_max_estoque", nullable = false)
    private Long maximo;

    @XmlElement(name = "ressuprimento" , required = true)
    @Column(name="ponto_de_ressuprimento", nullable = false)
    private Long pontoRessuprimento;

    @XmlElement(name = "reposicao" , required = true)
    @Column(name="ponto_de_reposicao", nullable = false)
    private Long prontoReposicao;

    public Estoque(final Long minimo, final Long disponivel, final Long maximo, final Long ressuprimento, final Long reposicao) {
        this();
        this.maximo = maximo;
        this.minimo = minimo;
        this.disponivel = disponivel;
        this.pontoRessuprimento = ressuprimento;
        this.prontoReposicao = reposicao;
    }

    public Estoque() {}
}
