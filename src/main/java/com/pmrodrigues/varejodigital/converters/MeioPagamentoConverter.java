package com.pmrodrigues.varejodigital.converters;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.ioc.RequestScoped;
import com.pmrodrigues.varejodigital.models.MeioPagamento;
import com.pmrodrigues.varejodigital.repositories.MeioPagamentoRepository;

@Convert(MeioPagamento.class)
@RequestScoped
public class MeioPagamentoConverter
        extends
        AbstractTypeConverter<MeioPagamento> {

    public MeioPagamentoConverter(final MeioPagamentoRepository repository) {
        super(repository);

    }

}
