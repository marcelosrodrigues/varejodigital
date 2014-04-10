package test.com.pmrodrigues.ellasa.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.com.pmrodrigues.ellasa.integration.pageobjects.PageFranqueadoPessoaFisica;

public class ITestFranqueadoPessoaFisicaController {

	private PageFranqueadoPessoaFisica page;
	
	@Before
	public void setup() {
		page = new PageFranqueadoPessoaFisica();
	}

	@After
	public void after() {
		page.close();
	}

	@Test
	public void deveGeraBoleto() {

		page.text("franqueado.nomeCompleto", "Marcelo da Silva Rodrigues")
				.text("cpf", "07032327702")
				.text("franqueado.email", "marsilvarodrigues@gmail.com")
				.text("dataNascimento", "17-08-1977")
				.text("cep", "22743-310")
				.select("franqueado.endereco.estado.uf", "RJ")
				.text("franqueado.endereco.cidade", "Rio de Janeiro")
				.text("franqueado.endereco.logradouro", "Estrada campo da areia")
				.text("franqueado.endereco.numero", "84")
				.text("franqueado.endereco.bairro", "Pechincha")
				.text("franqueado.endereco.complemento", "apto 206")
				.text("franqueado.residencial.ddd", "21")
				.text("franqueado.residencial.numero", "33926222")
				.radio("franquia", "4").select("meiodepagamento", "1")
				.text("indicacao", "4RGF6OP4SS").submit();

	}
}
