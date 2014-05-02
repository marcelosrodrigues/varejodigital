package com.pmrodrigues.ellasa.models;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "produto")
@NamedQueries({@NamedQuery(name = "Produto.All", query = "SELECT p FROM Produto p inner join fetch p.secao s left join fetch p.imagens i left join fetch p.atributos a ORDER BY s.id")})
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name = "loja_id")
	private Long shop_id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "secao_id",referencedColumnName="id")
	private Secao secao;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "descricao_curta")
	private String descricaoBreve;

	@Column(name = "preco")
	private BigDecimal preco;

	@Column(name = "peso")
	private BigDecimal peso;
	
	@OneToMany
	@JoinColumn(name = "produto_id")
	private final Set<Imagem> imagens = new HashSet<>();
	
	@OneToMany
	@JoinColumn(name = "produto_id")
	private final Set<Atributo> atributos = new HashSet<>();

}
