package com.pmrodrigues.ellasa.converters;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.ioc.RequestScoped;
import com.pmrodrigues.ellasa.models.MeioPagamento;
import com.pmrodrigues.ellasa.repositories.MeioPagamentoRepository;

@Convert(MeioPagamento.class)
@RequestScoped
public class MeioPagamentoConverter
        extends
        AbstractTypeConverter<MeioPagamento> {

    public MeioPagamentoConverter(final MeioPagamentoRepository repository) {
        super(repository);

    }

}
