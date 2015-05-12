package test.com.pmrodrigues.varejodigital.integration.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import test.com.pmrodrigues.varejodigital.integration.pageobjects.annotations.ByClass;
import test.com.pmrodrigues.varejodigital.integration.pageobjects.annotations.ById;
import test.com.pmrodrigues.varejodigital.integration.pageobjects.annotations.ByXPath;
import test.com.pmrodrigues.varejodigital.integration.pageobjects.annotations.URL;
import test.com.pmrodrigues.varejodigital.integration.pageobjects.tags.Tag;
import test.com.pmrodrigues.varejodigital.integration.pageobjects.tags.TagFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractPageObject {

    private WebDriver driver;

    public AbstractPageObject(final String url) {
        this();
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
                "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        driver = new ChromeDriver(options);
        driver.get(url);
    }

    public AbstractPageObject() {
    }

    public AbstractPageObject(final WebDriver driver) {
        this();
        this.driver = driver;
    }

    public void close() {
        this.driver.close();
        this.driver.quit();
    }

    public abstract AbstractPageObject submit() throws Exception;

    public AbstractPageObject textByName(final String id, final String value) {
        driver.findElement(By.name(id)).sendKeys(value);
        return this;
    }

    public AbstractPageObject clickById(final String id) {
        driver.findElement(By.id(id)).click();
        return this;
    }


    public void set(String url) {
        this.driver.get(url);
    }


    public AbstractPageObject to(Class<? extends AbstractPageObject> toPage) throws Exception {
        final AbstractPageObject page = toPage.getConstructor(WebDriver.class)
                .newInstance(this.driver);
        page.url(toPage.getAnnotation(URL.class).url());

        populate(page);

        return page;
    }

    protected void populate(AbstractPageObject page) throws IllegalAccessException {

        final List<Field> atributos = getDeclaredFields(page.getClass());

        for (final Field atributo : atributos) {

            atributo.setAccessible(true);

            Tag tag = null;

            if (atributo.isAnnotationPresent(ById.class)) {

                final ById byid = atributo.getAnnotation(ById.class);

                tag = TagFactory.getInstance(driver).createById(byid.value());

            } else if (atributo.isAnnotationPresent(ByXPath.class)) {
                final ByXPath byxpath = atributo.getAnnotation(ByXPath.class);
                tag = TagFactory.getInstance(driver).createByXPath(byxpath.value());
            } else if (atributo.isAnnotationPresent(ByClass.class)) {
                final ByClass byxpath = atributo.getAnnotation(ByClass.class);
                tag = TagFactory.getInstance(driver).createByClass(byxpath.value());
            }

            if (tag == null) continue;

            if (atributo.getType().equals(String.class)) {
                atributo.set(page, tag.getValue());
            } else if (atributo.getType().equals(Boolean.class)) {
                atributo.set(page, Boolean.valueOf(tag.getValue().toString()));
            }

        }
    }

    private List<Field> getDeclaredFields(Class<? extends AbstractPageObject> toPage) {
        final List<Field> atributos = new ArrayList<>();
        if (toPage.getSuperclass() != null) {
            atributos.addAll(Arrays.asList(toPage.getSuperclass().getDeclaredFields()));
        }
        atributos.addAll(Arrays.asList(toPage.getDeclaredFields()));
        return atributos;
    }

    private AbstractPageObject url(final String url) {
        driver.get(url);
        return this;
    }

    public void update(final AbstractPageObject pageObject) throws IllegalAccessException {

        final List<Field> atributos = getDeclaredFields(pageObject.getClass());

        for (final Field atributo : atributos) {

            atributo.setAccessible(true);

            final Object value = atributo.get(pageObject);
            Tag tag = null;


            if (atributo.isAnnotationPresent(ById.class)) {

                final ById byid = atributo.getAnnotation(ById.class);
                tag = TagFactory.getInstance(driver).createById(byid.value());
            } else if (atributo.isAnnotationPresent(ByXPath.class)) {
                final ByXPath byXPath = atributo.getAnnotation(ByXPath.class);
                tag = TagFactory.getInstance(driver).createByXPath(byXPath.value());
            } else if (atributo.isAnnotationPresent(ByClass.class)) {
                final ByClass byXPath = atributo.getAnnotation(ByClass.class);
                tag = TagFactory.getInstance(driver).createByClass(byXPath.value());
            }

            if (tag != null && value != null) {
                tag.setValue(value);
            } else {
                continue;
            }
        }

    }

    protected WebDriver getDriver() {
        return driver;
    }
}
