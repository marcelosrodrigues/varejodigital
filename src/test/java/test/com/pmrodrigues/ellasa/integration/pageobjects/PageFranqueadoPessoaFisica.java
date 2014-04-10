package test.com.pmrodrigues.ellasa.integration.pageobjects;

public class PageFranqueadoPessoaFisica extends AbstractPageObject {

	private static final String URL = "http://localhost:8080/ellasa/seja-um-franqueado.html";

	public PageFranqueadoPessoaFisica() {
		super(PageFranqueadoPessoaFisica.URL);
	}

	@Override
	public AbstractPageObject submit() {

		super.click("avancar");
		return this;
	}


}
