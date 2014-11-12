package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.repositories.ShoppingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Marceloo on 12/11/2014.
 */
@Repository("ShoppingRepository")
public class ShoppingRepositoryImpl extends AbstractRepository<Loja> implements ShoppingRepository {}
