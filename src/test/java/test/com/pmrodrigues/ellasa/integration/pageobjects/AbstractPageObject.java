package test.com.pmrodrigues.ellasa.integration.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.ui.Select;

public abstract class AbstractPageObject {

	private final WebDriver driver;

	public AbstractPageObject(final String url) {
		System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
				"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(url);
	}

	public void close() {
		this.driver.close();
		this.driver.quit();
	}

	public abstract AbstractPageObject submit();

	public AbstractPageObject text(final String id, final String value) {
		driver.findElement(By.id(id)).sendKeys(value);
		return this;

	}

	public AbstractPageObject select(final String id, final String value) {

		final Select select = new Select(driver.findElement(By.id(id)));
		for (WebElement option : select.getOptions()) {
			if (value.equalsIgnoreCase(option.getValue())) {
				option.click();
				break;
			}
		}
		return this;

	}

	public AbstractPageObject radio(final String id, final String value) {
		driver.findElement(
				By.xpath(String
						.format("//input[contains(@id,'%s') and contains(@value, '%s')]",
								id, value)))
				.click();
		return this;

	}

	public AbstractPageObject click(final String id) {
		driver.findElement(By.id(id)).click();
		return this;
	}

}
