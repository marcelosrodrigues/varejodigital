package test.com.pmrodrigues.ellasa.integration.pageobjects;

import org.openqa.selenium.WebDriver;

/**
 * Created by Marceloo on 26/12/2014.
 */
public class DashboardPage extends AbstractPageObject {

    public DashboardPage(final String url) {
        super(url);
    }

    public DashboardPage(final WebDriver driver) {
        super(driver);
    }

    @Override
    public AbstractPageObject submit() {
        return null;
    }
}
