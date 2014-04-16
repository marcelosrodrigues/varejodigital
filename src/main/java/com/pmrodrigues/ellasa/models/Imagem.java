package com.pmrodrigues.ellasa.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Imagem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="image")
	private String url;
}
