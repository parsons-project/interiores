/*
 */
package interiores.core.presentation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used in Swing views to tell that some method is listening to an event of same name.
 * The paramNames is used to pass information of the event to the method as arguments.
 * @author hector
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Event
{
    String[] paramNames() default {};
}
