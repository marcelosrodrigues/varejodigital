package com.pmrodrigues.ellasa.repositories;

import com.pmrodrigues.ellasa.models.Franqueado;

public interface FranqueadoRepository extends Repository<Franqueado> {

	public abstract Franqueado findByCodigo(String codigo);

}