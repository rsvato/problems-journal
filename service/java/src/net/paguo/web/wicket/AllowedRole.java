package net.paguo.web.wicket;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * User: sreentenko
 * Date: 04.06.2007
 * Time: 0:55:29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AllowedRole {
    String value();
}
