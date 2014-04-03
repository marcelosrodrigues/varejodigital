package com.pmrodrigues.ellasa.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("T")
public class TEF extends MeioPagamento {

	private static final long serialVersionUID = 1L;

}
