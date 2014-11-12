package com.pmrodrigues.ellasa.repositories.impl;

import org.springframework.stereotype.Repository;

import com.pmrodrigues.ellasa.models.Cliente;
import com.pmrodrigues.ellasa.repositories.ClienteRepository;

@Repository("ClienteRepository")
public class ClienteRepositoryImpl extends AbstractRepository<Cliente> implements ClienteRepository{

	private static final long serialVersionUID = 1L;

}
