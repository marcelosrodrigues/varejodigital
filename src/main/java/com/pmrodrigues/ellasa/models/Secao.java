package com.pmrodrigues.ellasa.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "secao")
@NamedQueries({@NamedQuery(name = "Secao.All", query = "SELECT s FROM Secao s left join fetch s.pai")})
public class Secao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
