/*
 */
package interiores.core.mvc;

import interiores.core.terminal.Terminal;
import java.beans.PropertyChangeListener;

/**
 *
 * @author hector
 */
public interface View extends PropertyChangeListener
{
    public void showView();
    public void setTerminal(Terminal terminal);
}
