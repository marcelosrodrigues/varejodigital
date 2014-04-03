package com.pmrodrigues.ellasa.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
public class Boleto extends MeioPagamento {

	private static final long serialVersionUID = 1L;

}
