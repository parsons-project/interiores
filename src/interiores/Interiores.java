package interiores;

import interiores.core.Application;
import interiores.core.Debug;
import interiores.core.data.JAXBDataController;
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
    public static void main(String[] args)
    {
        // Gimme info of the errors, yo!
        Debug.enable();
        
        // Application for wiring
        Application app = new Application("interiores");
        
        // Application dependencies
        JAXBDataController dataController = new MappedDataController();
        TerminalController terminal = new TerminalController("interiores.presentation.terminal.commands");
        SwingController swingController = new SwingController("interiores.presentation.swing.views");
        
        // Persistance layer
        app.setDataController(dataController);
        
        // Presentation layer
        app.addPresentation(swingController);
        app.addPresentation(terminal);
        
        // Business layer
        app.addBusiness("room");
        app.addBusiness("constraint");
        app.addBusiness("design");
        app.addBusiness("furnitureModel");
        app.addBusiness("furnitureType");
        app.addBusiness("roomType");
        app.addBusiness("furnitureTypesCatalog");
        app.addBusiness("roomTypesCatalog");
        
        // Some terminal shortcuts!
        terminal.addShortcut("furnitureModel", "fm");
        terminal.addShortcut("furnitureType", "ft");
        terminal.addShortcut("roomType", "rt");
        terminal.addShortcut("furnitureTypesCatalog", "ftc");
        terminal.addShortcut("roomTypesCatalog", "rtc");
        terminal.addShortcut("constraint", "c");
        
        // Run, run, run!!!
        app.init();
    }
}
