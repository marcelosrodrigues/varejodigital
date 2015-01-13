package test.com.pmrodrigues.ellasa.integration.pageobjects;

import org.openqa.selenium.WebDriver;
import test.com.pmrodrigues.ellasa.integration.pageobjects.annotations.URL;

/**
 * Created by Marceloo on 12/01/2015.
 */
@URL(url = "http://localhost:8080/secao/index.do")
public class SecoesPage extends AbstractPageObject {

    public SecoesPage(final WebDriver driver){
        super(driver);
    }

    @Override
    public AbstractPageObject submit() throws Exception {
        return null;
    }
}
