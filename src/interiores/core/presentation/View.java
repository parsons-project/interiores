/*
 */
package interiores.core.presentation;

import java.beans.PropertyChangeListener;

/**
 *
 * @author hector
 */
public interface View extends PropertyChangeListener
{
    public void showView();
    public void setPresentation(PresentationController presentation);
}
