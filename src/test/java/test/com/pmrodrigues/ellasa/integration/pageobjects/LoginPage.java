package test.com.pmrodrigues.ellasa.integration.pageobjects;

import org.openqa.selenium.WebDriver;

/**
 * Created by Marceloo on 26/12/2014.
 */
public class LoginPage extends AbstractPageObject {

    public LoginPage(String url) {
        super(url);
    }

    public LoginPage(final WebDriver driver) {
        super(driver);
    }

    public LoginPage email(String email){
        this.textByName("j_username", email);
        return this;
    }

    public LoginPage password(String password){
        this.textByName("j_password", password);
        return this;
    }

    @Override
    public AbstractPageObject submit() throws Exception {
        this.clickById("login");
        return super.to(DashboardPage.class);
    }

}
