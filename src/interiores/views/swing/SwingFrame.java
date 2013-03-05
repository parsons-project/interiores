/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.views.swing;

import interiores.core.mvc.View;
import interiores.core.terminal.Terminal;
import java.beans.PropertyChangeEvent;
import javax.swing.JFrame;

/**
 *
 * @author hector0193
 */
abstract public class SwingFrame extends JFrame implements View
{
    private Terminal terminal;
    
    @Override
    public void showView()
    {
        setVisible(true);
    }
    
    public void setTerminal(Terminal terminal)
    {
        this.terminal = terminal;
    }
    
    public void exec(String cmd)
    {
        terminal.exec(cmd);
    }
}
