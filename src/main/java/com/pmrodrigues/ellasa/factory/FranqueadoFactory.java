package com.pmrodrigues.ellasa.factory;

import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.pagamentos.entity.Address;
import com.pmrodrigues.ellasa.pagamentos.entity.Payer;
import com.pmrodrigues.ellasa.pagamentos.entity.Phone;
import org.apache.commons.validator.GenericValidator;

/**
 * Created by Marceloo on 11/11/2014.
 */
class FranqueadoFactory extends PayerFactory {

    private final OrdemPagamento pagamento;

    public FranqueadoFactory(final OrdemPagamento pagamento) {
        super();
        this.pagamento = pagamento;
    }

    @Override
    public Payer create() {

        final Payer pagador = new Payer();
        pagador.setEmail(pagamento.getContrato().getFranqueado().getEmail());
        pagador.setName(pagamento.getContrato().getFranqueado().getNome());


        final Address address = new Address();
        address.setCity(pagamento.getContrato().getFranqueado().getEndereco().getCidade());
        address.setComplement(pagamento.getContrato().getFranqueado().getEndereco().getComplemento());
        address.setCountry(Address.Country.BRA);
        address.setNeighbourhood(pagamento.getContrato().getFranqueado().getEndereco().getBairro());
        address.setNumber(Integer.parseInt(pagamento.getContrato().getFranqueado().getEndereco().getNumero()));
        address.setState(Address.State.valueOf(pagamento.getContrato().getFranqueado().getEndereco().getEstado().getUf()));
        address.setStreet(pagamento.getContrato().getFranqueado().getEndereco().getLogradouro());
        address.setType(Address.Type.SHIPPING);
        address.setZip(pagamento.getContrato().getFranqueado().getEndereco().getCep());

        pagador.addAddress(address);

        if (pagamento.getContrato().getFranqueado().getCelular() != null
                && !GenericValidator.isBlankOrNull(pagamento.getContrato()
                .getFranqueado().getCelular().getDdd())
                && !GenericValidator.isBlankOrNull(pagamento.getContrato()
                .getFranqueado().getCelular().getNumero())) {

            pagador.addPhone(getPhone(Phone.Type.CELLPHONE,pagamento.getContrato().getFranqueado().getCelular().getDdd() + pagamento.getContrato().getFranqueado().getCelular().getNumero()));

        }

        if (pagamento.getContrato().getFranqueado().getResidencial().getDdd() != null
                && !GenericValidator.isBlankOrNull(pagamento.getContrato()
                .getFranqueado().getResidencial().getDdd())
                && !GenericValidator.isBlankOrNull(pagamento.getContrato()
                .getFranqueado().getResidencial().getNumero())) {

            pagador.addPhone(getPhone(Phone.Type.RESIDENTIAL,pagamento.getContrato().getFranqueado().getResidencial().getDdd() + pagamento.getContrato().getFranqueado().getResidencial().getNumero()));

        }


        return pagador;
    }

}
