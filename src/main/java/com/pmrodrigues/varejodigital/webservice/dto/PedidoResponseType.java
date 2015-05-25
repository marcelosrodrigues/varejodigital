package com.pmrodrigues.varejodigital.webservice.dto;

import com.pmrodrigues.varejodigital.webservice.adapters.DateTypeAdapter;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * Created by Marceloo on 25/05/2015.
 */
@XmlType(name = "VendasResponseType"  , namespace = "http://schema.varejodigital.projetandoo/1.0/")
@XmlAccessorType(XmlAccessType.FIELD)
public class PedidoResponseType {

    @XmlElement(name = "ServiceStatus")
    private Status serviceStatus;

    @XmlElement(name = "dataProcessamento")
    @XmlSchemaType(name = "dateTime")
    @XmlJavaTypeAdapter(value = DateTypeAdapter.class, type = Date.class)
    private final Date dataProcessamento = DateTime.now().toDate();

    @XmlElement(name = "motivo")
    private String message;

    public PedidoResponseType(final Status status, final String mensagem) {
        this();
        this.serviceStatus = status;
        this.message = mensagem;
    }

    public PedidoResponseType() {

    }

    public Status getStatus() {
        return serviceStatus;
    }
}
