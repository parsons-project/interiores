/*
 */
package interiores;

import interiores.core.Application;
import interiores.core.ViewLoader;
import interiores.core.terminal.Terminal;
import interiores.views.swing.SwingLoader;

/**
 *
 * @author hector
 */
public class Interiores
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        ViewLoader loader = new SwingLoader();
        
        Terminal terminal = new Terminal();
        //terminal.setViewLoader(loader);
        
        Application app = new Application(terminal);
        app.init();
    }
}
