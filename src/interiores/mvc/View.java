/*
 */
package interiores.mvc;

import java.beans.PropertyChangeListener;

/**
 *
 * @author hector
 */
public interface View extends PropertyChangeListener
{
    public void setVisible(boolean visible);
}
