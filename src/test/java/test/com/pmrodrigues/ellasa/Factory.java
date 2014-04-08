package test.com.pmrodrigues.ellasa;

import org.joda.time.DateTime;

import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.models.FranqueadoPessoaFisica;
import com.pmrodrigues.ellasa.models.Residencial;

public final class Factory {

	private Factory() {
	}

	public static FranqueadoPessoaFisica getStubFranqueado(final Estado estado) {

		FranqueadoPessoaFisica franqueado = new FranqueadoPessoaFisica();
		franqueado.setEmail("marcelosrodrigues@globo.com");
		franqueado.setNomeCompleto("Marcelo da Silva Rodrigues");
		franqueado.getEndereco().setEstado(estado);
		franqueado.getEndereco().setLogradouro("teste");
		franqueado.getEndereco().setCep("22743310");
		franqueado.getEndereco().setBairro("teste");
		franqueado.getEndereco().setCidade("teste");
		franqueado.getEndereco().setNumero("84");
		franqueado.getEndereco().setComplemento("teste");
		franqueado.setCPF("070.323.277-02");
		franqueado.setDataNascimento(DateTime.now().minusYears(30).toDate());
		Residencial telefone = new Residencial();
		telefone.setDdd("021");
		telefone.setNumero("33926222");
		franqueado.setResidencial(telefone);

		return franqueado;

	}
}
