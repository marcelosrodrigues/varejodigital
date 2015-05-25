package com.pmrodrigues.varejodigital.webservice.dto;

import com.pmrodrigues.varejodigital.models.Produto;
import com.pmrodrigues.varejodigital.webservice.adapters.DateTypeAdapter;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * Created by Marceloo on 20/05/2015.
 */
@XmlType(name = "ProdutoResponseType"  , namespace = "http://schema.varejodigital.projetandoo/1.0/")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProdutoResponseType {

    @XmlElement(name = "ServiceStatus")
    private Status serviceStatus;

    @XmlElement(name = "dataProcessamento")
    @XmlSchemaType(name = "dateTime")
    @XmlJavaTypeAdapter(value = DateTypeAdapter.class, type = Date.class)
    private final Date dataProcessamento = DateTime.now().toDate();

    @XmlElement(name = "produto")
    private Produto produto;

    @XmlElement(name = "motivo")
    private String message;

    public ProdutoResponseType(final Status status, final Produto produto, final String mensagem) {
        this();
        this.serviceStatus = status;
        this.produto = produto;
        this.message = mensagem;
    }

    public ProdutoResponseType() {

    }

    public Status getStatus() {
        return serviceStatus;
    }
}
