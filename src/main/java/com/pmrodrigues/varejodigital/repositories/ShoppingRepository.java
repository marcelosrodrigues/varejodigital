package com.pmrodrigues.varejodigital.repositories;

import com.pmrodrigues.varejodigital.models.Loja;

import java.util.List;

/**
 * Created by Marceloo on 12/11/2014.
 */
public interface ShoppingRepository extends Repository<Loja> {

    List<Loja> listByNome(final String nome);
}
