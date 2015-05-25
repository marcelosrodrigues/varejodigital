package com.pmrodrigues.varejodigital.webservice.dto;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by Marceloo on 20/05/2015.
 */
@XmlType(name = "StatusType" , namespace = "http://schema.varejodigital.projetandoo/1.0/")
public enum Status {
    SUCESSO , ERROR , WARNING
}
