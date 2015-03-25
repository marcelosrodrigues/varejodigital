package com.pmrodrigues.ellasa.models;

import com.pmrodrigues.ellasa.pagamentos.entity.Transaction.PaymentMethod;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@NamedQueries({@NamedQuery(name = "MeioPagamento.All", query = "FROM MeioPagamento ORDER BY id ASC")})
public class MeioPagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String descricao;

    @Enumerated(EnumType.ORDINAL)
    private PaymentMethod method;

    public void setTipo(final PaymentMethod method) {
        this.method = method;
    }

    public PaymentMethod getTipo() {
        return method;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

}
