package test.com.pmrodrigues.varejodigital.integration.pageobjects.annotations;

import java.lang.annotation.*;

/**
 * Created by Marceloo on 05/01/2015.
 */

@Target({ElementType.FIELD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ByXPath {

    String value();
}
