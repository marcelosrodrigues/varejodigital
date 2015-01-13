package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.models.CEP;
import com.pmrodrigues.ellasa.repositories.CEPRepository;
import org.springframework.stereotype.Repository;

@Repository("CEPRepository")
public class CEPRepositoryImpl extends AbstractRepository<CEP>
        implements
        CEPRepository {

    private static final long serialVersionUID = 1L;

}
