package interiores.core.presentation.annotation;

import interiores.core.Event;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to tell that some method is listening to an event.
 * @author hector
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Listen
{
    Class<? extends Event>[] value();
}
