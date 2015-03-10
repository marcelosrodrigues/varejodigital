package com.pmrodrigues.ellasa.repositories;

import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.models.Secao;

import java.util.List;

public interface SecaoRepository extends Repository<Secao> {

    List<Secao> listAll();

    List<Secao> findByLoja(final Loja loja);

    List<Secao> listByNome(Loja loja, String nome);

    List<Secao> listAllSubSecoesByLojaAndPai(Loja loja, Secao pai);
}
