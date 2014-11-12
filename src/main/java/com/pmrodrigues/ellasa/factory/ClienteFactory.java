package com.pmrodrigues.ellasa.factory;

import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.pagamentos.entity.Address;
import com.pmrodrigues.ellasa.pagamentos.entity.Payer;
import com.pmrodrigues.ellasa.pagamentos.entity.Phone;
import org.apache.commons.validator.GenericValidator;

/**
 * Created by Marceloo on 11/11/2014.
 */
public class ClienteFactory extends PayerFactory {
    private final OrdemPagamento pagamento;

    public ClienteFactory(OrdemPagamento pagamento) {
        super();
        this.pagamento = pagamento;
    }


    @Override
    public Payer create() {
        final Payer cliente = new Payer();
        cliente.setEmail(pagamento.getPedido().getCliente().getEmail());
        cliente.setName(pagamento.getPedido().getCliente().getNome());

        final Address address = new Address();
        address.setCity(pagamento.getPedido().getCliente().getEndereco().getCidade());
        address.setComplement(" ");
        address.setCountry(Address.Country.BRA);
        address.setNeighbourhood(pagamento.getPedido().getCliente().getEndereco().getBairro());
        address.setNumber(0);
        address.setState(Address.State.valueOf(pagamento.getPedido().getCliente().getEndereco().getEstado().getUf()));
        address.setStreet(pagamento.getPedido().getCliente().getEndereco().getLogradouro());
        address.setType(Address.Type.SHIPPING);
        address.setZip(pagamento.getPedido().getCliente().getEndereco().getCep());

        cliente.addAddress(address);

        if( !GenericValidator.isBlankOrNull(pagamento.getPedido().getCliente().getEndereco().getCelular()) ){
            cliente.addPhone(getPhone(Phone.Type.CELLPHONE,pagamento.getPedido().getCliente().getEndereco().getCelular()));
        }
        if( !GenericValidator.isBlankOrNull(pagamento.getPedido().getCliente().getEndereco().getTelefone()) ){
            cliente.addPhone(getPhone(Phone.Type.RESIDENTIAL,pagamento.getPedido().getCliente().getEndereco().getTelefone()));
        }





        return cliente;
    }
}
