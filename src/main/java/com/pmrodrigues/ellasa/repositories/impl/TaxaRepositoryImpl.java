package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.models.Taxa;
import com.pmrodrigues.ellasa.repositories.TaxaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Marceloo on 14/10/2014.
 */
@Repository("TaxaRepository")
public class TaxaRepositoryImpl extends AbstractRepository<Taxa> implements TaxaRepository {
}
