package test.com.pmrodrigues.ellasa.integration.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

public abstract class AbstractPageObject {

	private WebDriver driver;

	public AbstractPageObject(final String url) {
		System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
				"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        driver = new ChromeDriver(options);
		driver.get(url);
	}

    public AbstractPageObject() {}

    public AbstractPageObject(final WebDriver driver) {
        this.driver = driver;
    }

    public void close() {
		this.driver.close();
		this.driver.quit();
	}

	public abstract AbstractPageObject submit();

	public AbstractPageObject textById(final String id, final String value) {
		driver.findElement(By.id(id)).sendKeys(value);
		return this;
	}

    public AbstractPageObject textByName(final String id, final String value){
        driver.findElement(By.name(id)).sendKeys(value);
        return this;
    }

	public AbstractPageObject selectById(final String id, final String value) {

		final Select select = new Select(driver.findElement(By.id(id)));
		for (WebElement option : select.getOptions()) {
			if (value.equalsIgnoreCase(option.getAttribute("value"))) {
				option.click();
				break;
			}
		}
		return this;

	}

	public AbstractPageObject radioById(final String id, final String value) {
		driver.findElement(
				By.xpath(String
						.format("//input[contains(@id,'%s') and contains(@value, '%s')]",
								id, value)))
				.click();
		return this;

	}

	public AbstractPageObject clickById(final String id) {
		driver.findElement(By.id(id)).click();
		return this;
	}


    public void set(String url) {
        this.driver.get(url);
    }

    public AbstractPageObject to(final String url) {
        switch(url) {
            case "http://localhost:8080":
                return new DashboardPage(driver);
            case "http://localhost:8080/login.jsp":
                return new LoginPage(driver);
            default:
                return null;
        }
    }
}
