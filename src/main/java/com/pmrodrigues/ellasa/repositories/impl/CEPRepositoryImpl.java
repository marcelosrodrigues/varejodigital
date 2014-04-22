package com.pmrodrigues.ellasa.repositories.impl;

import org.springframework.stereotype.Repository;

import com.pmrodrigues.ellasa.models.CEP;
import com.pmrodrigues.ellasa.repositories.CEPRepository;

@Repository("CEPRepository")
public class CEPRepositoryImpl extends AbstractRepository<CEP>
		implements
			CEPRepository {

	private static final long serialVersionUID = 1L;

}
