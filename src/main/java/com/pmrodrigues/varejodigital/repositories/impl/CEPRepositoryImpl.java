package com.pmrodrigues.varejodigital.repositories.impl;

import com.pmrodrigues.varejodigital.models.CEP;
import com.pmrodrigues.varejodigital.repositories.CEPRepository;
import org.springframework.stereotype.Repository;

@Repository("CEPRepository")
public class CEPRepositoryImpl extends AbstractRepository<CEP>
        implements
        CEPRepository {

    private static final long serialVersionUID = 1L;

}
