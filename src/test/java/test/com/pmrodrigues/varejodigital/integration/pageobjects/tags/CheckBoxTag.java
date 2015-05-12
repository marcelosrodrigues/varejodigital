package test.com.pmrodrigues.varejodigital.integration.pageobjects.tags;

import org.openqa.selenium.WebElement;

/**
 * Created by Marceloo on 06/01/2015.
 */
public class CheckBoxTag implements Tag {
    private final WebElement element;

    public CheckBoxTag(final WebElement element) {
        this.element = element;
    }

    @Override
    public Object getValue() {
        if ("checked".equalsIgnoreCase(element.getAttribute("checked"))) {
            return element.getAttribute("value");
        }
        return null;
    }

    @Override
    public void setValue(final Object value) {
        if (value instanceof Boolean) {
            Boolean checked = (Boolean) value;
            if (checked) {
                if (!element.isSelected()) {
                    element.click();
                }
            } else {
                if (element.isSelected()) {
                    element.click();
                }
            }
        } else {
            if (value.equals(element.getAttribute("value"))) {
                this.element.click();
            }
        }
    }
}
