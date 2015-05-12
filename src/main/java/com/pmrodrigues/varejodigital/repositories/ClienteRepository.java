package com.pmrodrigues.varejodigital.repositories;

import com.pmrodrigues.varejodigital.models.Cliente;

public interface ClienteRepository extends Repository<Cliente> {
    Cliente findByEmail(String email);
}
