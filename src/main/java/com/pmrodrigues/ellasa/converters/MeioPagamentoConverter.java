package com.pmrodrigues.ellasa.converters;

import com.pmrodrigues.ellasa.models.MeioPagamento;
import com.pmrodrigues.ellasa.repositories.MeioPagamentoRepository;

public class MeioPagamentoConverter
		extends
			AbstractTypeConverter<MeioPagamento> {

	public MeioPagamentoConverter(MeioPagamentoRepository repository) {
		super(repository);

	}

}
