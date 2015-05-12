package test.com.pmrodrigues.varejodigital.integration.pageobjects;

import test.com.pmrodrigues.varejodigital.integration.pageobjects.annotations.ById;
import test.com.pmrodrigues.varejodigital.integration.pageobjects.annotations.ByXPath;
import test.com.pmrodrigues.varejodigital.integration.pageobjects.annotations.URL;

/**
 * Created by Marceloo on 30/01/2015.
 */
@URL(url = "http://localhost:8080/loja/novo.do")
public class NovaLojaPage extends AbstractPageObject {

    @ById("object.nome")
    private String nome;

    @ByXPath("xpath=(//input[@id='object.secoes[]' and @value='153'])")
    private String secoes;


    public NovaLojaPage nome(String nome) {
        this.nome = nome;
        return this;
    }

    public NovaLojaPage secao(String secao) {
        this.secoes = secao;
        return this;
    }

    @Override
    public AbstractPageObject submit() throws Exception {
        super.update(this);
        this.clickById("salvar");
        return super.to(LojasPage.class);
    }
}
