package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.models.Cliente;
import com.pmrodrigues.ellasa.repositories.ClienteRepository;
import org.springframework.stereotype.Repository;

@Repository("ClienteRepository")
public class ClienteRepositoryImpl extends AbstractRepository<Cliente> implements ClienteRepository {

    private static final long serialVersionUID = 1L;

}
