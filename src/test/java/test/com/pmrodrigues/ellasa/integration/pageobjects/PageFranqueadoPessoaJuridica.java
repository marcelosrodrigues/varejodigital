package test.com.pmrodrigues.ellasa.integration.pageobjects;

public class PageFranqueadoPessoaJuridica extends AbstractPageObject {

	private static final String URL = "http://localhost:8080/ellasa/seja-um-parceiro.html";

	public PageFranqueadoPessoaJuridica() {
		super(PageFranqueadoPessoaJuridica.URL);
	}

	@Override
	public AbstractPageObject submit() {

		super.click("avancar");
		return this;
	}


}
