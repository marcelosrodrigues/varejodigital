package com.pmrodrigues.ellasa.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "secao")
@NamedQueries({@NamedQuery(name = "Secao.All", query = "SELECT s FROM Secao s left join fetch s.pai")})
public class Secao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name = "secao")
	private String nome;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pai_id")
	private Secao pai;

	@OneToMany
	@JoinColumn(name = "pai_id")
	private final Set<Secao> subsecoes = new HashSet<>();

}
