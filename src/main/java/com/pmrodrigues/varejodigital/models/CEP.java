package com.pmrodrigues.varejodigital.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cep")
@NamedQueries({@NamedQuery(name = "CEP.All", query = "SELECT c FROM CEP c inner join fetch c.estado ORDER BY c.estado.uf")})
public class CEP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id; //NOPMD

    @ManyToOne(optional = false)
    @JoinColumn(name = "estado_id")
    private Estado estado; //NOPMD

    @Column(nullable = false)
    private Long inicial; //NOPMD

    @Column(nullable = false, name = "final")
    private Long fim; //NOPMD

}
