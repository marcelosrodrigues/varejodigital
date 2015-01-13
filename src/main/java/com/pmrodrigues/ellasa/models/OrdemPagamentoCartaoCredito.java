package com.pmrodrigues.ellasa.models;

import br.com.caelum.stella.bean.validation.CPF;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table
@XStreamAlias("pagamento")
public class OrdemPagamentoCartaoCredito extends OrdemPagamento {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Número do cartão de crédito obrigatório")
    @CreditCardNumber(message = "Número do cartão de crédito inválido")
    @Column(nullable = false)
    private String numero;

    @NotBlank(message = "Codigo de segurança inválido")
    @Length(max = 3, min = 3, message = "Código de segurança inválido")
    @Column(nullable = false)
    private String codigosegura;

    @NotNull(message = "Data de expiração do cartão de crédito é obrigatório")
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataExpiracao;

    @NotBlank(message = "Nome do portador do cartão de crédito é invalido")
    @Column(nullable = false)
    private String portador;

    @NotBlank(message = "CPF é obrigatório")
    @CPF(formatted = true, message = "CPF inválido")
    @Column(nullable = false)
    private String cpf;

    @NotBlank(message = "Telefone é obrigatório")
    @Column(nullable = false)
    private String telefone;

    public OrdemPagamentoCartaoCredito(final MeioPagamento meiopagamento,
                                       final Contrato contrato, final BigDecimal valor) {
        super(meiopagamento, contrato, valor);
    }

    public OrdemPagamentoCartaoCredito() {
        super();
    }

    public void setNumero(final String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setCodigoSeguranca(final String codigoseguranca) {
        this.codigosegura = codigoseguranca;
    }

    public String getCodigoSeguranca() {
        return codigosegura;
    }

    public void setDataExpiracao(final Date dataexpiracao) {
        this.dataExpiracao = dataexpiracao;
    }

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setPortador(final String portador) {
        this.portador = portador;
    }

    public void setCPF(final String cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(final String telefone) {
        this.telefone = telefone;
    }

    public String getCPF() {
        return cpf;
    }

    public String getPortador() {
        return portador;
    }

    public String getTelefone() {
        return telefone;
    }
}
