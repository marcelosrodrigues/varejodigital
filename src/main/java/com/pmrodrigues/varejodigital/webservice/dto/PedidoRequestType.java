package com.pmrodrigues.varejodigital.webservice.dto;

import com.pmrodrigues.varejodigital.Constante;
import com.pmrodrigues.varejodigital.models.ItemPedido;
import com.pmrodrigues.varejodigital.models.Loja;
import com.pmrodrigues.varejodigital.models.Pedido;
import com.pmrodrigues.varejodigital.webservice.adapters.DateTypeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Marceloo on 25/05/2015.
 */
@XmlRootElement()
@XmlType(name = "VendaType" , namespace = "http://schema.varejodigital.projetandoo/1.0/")
@XmlAccessorType(XmlAccessType.FIELD)
public class PedidoRequestType implements Serializable {

    @XmlElement(name = "item" , namespace = "http://schema.varejodigital.projetandoo/1.0/")
    private final List<ItemPedido> itens = new ArrayList<>();

    @XmlElement(name = "loja" , required = true)
    private Loja loja;

    @XmlElement(name = "dataCompra")
    @XmlSchemaType(name = "dateTime")
    @XmlJavaTypeAdapter(value = DateTypeAdapter.class, type = Date.class)
    private Date dataCompra = Constante.DATA_INICIAL;

    public Pedido novoPedido() {
        final Pedido pedido = new Pedido();
        pedido.getItens().addAll(itens);
        pedido.setLoja(loja);
        pedido.setDataCompra(dataCompra);

        return pedido;
    }
}
