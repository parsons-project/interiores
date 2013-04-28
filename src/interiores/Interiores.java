/*
 */
package interiores;

import interiores.core.Application;
import interiores.core.Debug;
import interiores.core.presentation.SwingController;
import interiores.core.presentation.TerminalController;
import interiores.data.MappedDataController;

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
        Debug.enable();
        
        Application app = new Application("interiores");
        TerminalController terminal = new TerminalController("interiores.presentation.terminal");
        
        app.setDataController(new MappedDataController());
        
        app.addPresentation(new SwingController("interiores.presentation.swing.views"));
        app.addPresentation(terminal);
        
        app.addBusiness("room");
        app.addBusiness("furnitureType");
        app.addBusiness("furnitureTypesCatalog");
        
        terminal.addShortcut("furnitureType", "ft");
        terminal.addShortcut("furnitureTypesCatalog", "ftc");
        
        app.init();
    }
}
