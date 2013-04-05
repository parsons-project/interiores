/*
 */
package interiores;

import interiores.core.Application;
import interiores.core.presentation.SwingController;
import interiores.core.presentation.TerminalController;

/**
 *
 * @author hector
 */
public class Interiores
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        Application app = new Application("interiores");
        
        app.addPresentation(new SwingController("interiores.presentation.swing.views"));
        app.addPresentation(new TerminalController("interiores.presentation.terminal"));
        
        app.addBusiness("room");
        
        app.init();
    }
}
