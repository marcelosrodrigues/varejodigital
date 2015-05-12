package test.com.pmrodrigues.varejodigital.integration.pageobjects.tags;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by Marceloo on 06/01/2015.
 */
public class SelectTag implements Tag {
    private final WebElement element;

    public SelectTag(final WebElement element) {
        this.element = element;
    }

    @Override
    public Object getValue() {
        final Select select = new Select(element);

        for (final WebElement option : select.getOptions()) {
            if (option.isSelected()) {
                return option.getAttribute("value");
            }
        }
        return null;
    }

    @Override
    public void setValue(Object value) {
        final Select select = new Select(element);

        for (final WebElement option : select.getOptions()) {
            if (value.equals(option.getAttribute("value"))) {
                option.click();
                break;
            }
        }
    }
}
