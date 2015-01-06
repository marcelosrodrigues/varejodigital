package test.com.pmrodrigues.ellasa.integration.pageobjects;

import org.openqa.selenium.WebDriver;
import test.com.pmrodrigues.ellasa.integration.pageobjects.annotations.URL;

/**
 * Created by Marceloo on 05/01/2015.
 */
@URL(url = "http://localhost:8080/usuario/index.do")
public class UsuariosPage extends AbstractPageObject {

    public UsuariosPage(WebDriver driver){
        super(driver);
    }

    @Override
    public AbstractPageObject submit() throws Exception {
        return null;
    }
}
