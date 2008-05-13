package net.paguo.visual;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Reyentenko
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InterfaceField {
    EditorEnum editor() default EditorEnum.STRING;

    int order() default 0;

    int length() default 0;
}
