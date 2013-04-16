/*
 */
package interiores.core.presentation;

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 *
 * @author hector
 */
public interface View
{
    public void showView();
    public void setPresentation(PresentationController presentation);
    public String[] getEvents();
}
