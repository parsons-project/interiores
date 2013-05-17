/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.core.presentation.terminal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author hector0193
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command
{
    String value() default "No description available";
}
