package com.pmrodrigues.varejodigital.models;

import org.apache.commons.validator.GenericValidator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Marceloo on 18/12/2014.
 */

@Entity
@Table(name = "perfil")
public class Perfil implements Serializable {

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(name = "membros", joinColumns = @JoinColumn(name = "perfil_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "perfil_id"}))
    @LazyCollection(LazyCollectionOption.EXTRA)
    private final Set<Usuario> membros = new HashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", unique = true)
    private String nome;

    public Perfil(final String nome) {
        this();
        this.nome = nome;
    }

    public Perfil() {
        //NOPMD
    }

    public Set<Usuario> getMembros() {
        return Collections.unmodifiableSet(this.membros);
    }

    public String getNome() {
        return nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Perfil) {
            final Perfil other = (Perfil) obj;
            return other.nome.equalsIgnoreCase(this.nome);
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (GenericValidator.isBlankOrNull(this.nome)) {
            return 0;
        } else {
            return this.nome.hashCode();
        }
    }

    public void remover(final Collection<Usuario> usuarios) {
        this.membros.removeAll(usuarios);
    }

    public void adicionar(final Collection<Usuario> usuarios) {
        this.membros.addAll(usuarios);
    }

    public GrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(this.nome);
    }
}
