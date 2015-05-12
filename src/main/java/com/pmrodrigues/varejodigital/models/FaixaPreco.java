package com.pmrodrigues.varejodigital.models;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "faixa_preco")
@NamedQueries({@NamedQuery(name = "FaixaPreco.All", query = "SELECT f FROM FaixaPreco f inner join fetch f.origem inner join fetch f.destino ORDER BY f.origem.uf, f.destino.uf")})
public class FaixaPreco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id; //NOPMD

    @ManyToOne(optional = false)
    @JoinColumn(name = "uf_destino")
    private Estado destino; //NOPMD

    @ManyToOne(optional = false)
    @JoinColumn(name = "uf_origem")
    private Estado origem; //NOPMD

    @Column(nullable = false, name = "preco")
    private BigDecimal preco; //NOPMD


}
