package com.pmrodrigues.ellasa.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("R")
public class Residencial extends Telefone {

	private static final long serialVersionUID = 1L;

}
