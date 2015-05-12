package test.com.pmrodrigues.varejodigital.integration.pageobjects.tags;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by Marceloo on 06/01/2015.
 */
public final class TagFactory {

    private static TagFactory instance;
    private final WebDriver driver;

    private TagFactory(final WebDriver driver) {
        this.driver = driver;
    }

    public static final TagFactory getInstance(final WebDriver driver) {
        if (instance == null) {
            instance = new TagFactory(driver);
        }
        return instance;
    }

    public Tag createById(final String id) {

        final WebElement element = driver.findElement(By.id(id));
        return createTag(element);
    }

    protected Tag createTag(WebElement element) {
        final String tagname = element.getTagName();
        final String type = element.getAttribute("type");

        if ("input".equalsIgnoreCase(tagname)) {
            if (!"checkbox".equalsIgnoreCase(type) && !"radio".equalsIgnoreCase(type)) {
                return new InputTag(element);
            } else if ("checkbox".equalsIgnoreCase(type)) {
                return new CheckBoxTag(element);
            } else if ("radio".equalsIgnoreCase(type)) {
                return new RadioButtonTag(element);
            }
        } else if ("select".equalsIgnoreCase(tagname)) {
            return new SelectTag(element);
        }
        return null;
    }

    public Tag createByXPath(String xpath) {
        final WebElement element = driver.findElement(By.xpath(xpath));
        return createTag(element);
    }

    public Tag createByClass(String css) {
        final WebElement element = driver.findElement(By.cssSelector(css));
        return createTag(element);
    }
}
