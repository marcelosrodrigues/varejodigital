package test.com.pmrodrigues.varejodigital.integration.pageobjects.annotations;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface URL {
    String url() default "http://localhost:8080";
}
