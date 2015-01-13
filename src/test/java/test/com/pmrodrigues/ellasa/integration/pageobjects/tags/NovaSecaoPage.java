package test.com.pmrodrigues.ellasa.integration.pageobjects.tags;

import org.openqa.selenium.WebDriver;
import test.com.pmrodrigues.ellasa.integration.pageobjects.AbstractPageObject;
import test.com.pmrodrigues.ellasa.integration.pageobjects.SecoesPage;
import test.com.pmrodrigues.ellasa.integration.pageobjects.annotations.ById;
import test.com.pmrodrigues.ellasa.integration.pageobjects.annotations.URL;

/**
 * Created by Marceloo on 12/01/2015.
 */
@URL(url = "http://localhost:8080/secao/novo.do")
public class NovaSecaoPage extends AbstractPageObject {

    @ById("object.nome")
    private String nome;

    @ById("object.pai")
    private String pai;


    public NovaSecaoPage(WebDriver driver) {
        super(driver);
    }


    @Override
    public AbstractPageObject submit() throws Exception {
        super.update(this);
        clickById("salvar");
        final SecoesPage page = new SecoesPage(super.getDriver());
        this.populate(page);
        return page;
    }

    public NovaSecaoPage nome(String nome) {
        this.nome = nome;
        return this;
    }

    public NovaSecaoPage pai(final String pai) {
        this.pai = pai;
        return this;
    }
}
