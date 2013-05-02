/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.core.presentation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to give a description of a command in a commandGroup
 * @author larribas
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Help
{
    String command();
    String description();
}
