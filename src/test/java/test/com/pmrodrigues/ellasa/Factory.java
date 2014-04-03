package test.com.pmrodrigues.ellasa;

import org.joda.time.DateTime;

import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.models.Telefone;

public final class Factory {

	private Factory() {
	}

	public static Franqueado getStubFranqueado(final Estado estado) {

		Franqueado franqueado = new Franqueado();
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
		Telefone telefone = new Telefone();
		telefone.setDdd("021");
		telefone.setNumero("33926222");
		franqueado.adicionar(telefone);

		return franqueado;

	}
}
