package interiores;

import interiores.business.controllers.BinaryConstraintController;
import interiores.business.controllers.DesignController;
import interiores.business.controllers.FixedElementController;
import interiores.business.controllers.FurnitureModelController;
import interiores.business.controllers.FurnitureTypeController;
import interiores.business.controllers.FurnitureTypesCatalogController;
import interiores.business.controllers.RoomController;
import interiores.business.controllers.RoomTypeController;
import interiores.business.controllers.RoomTypesCatalogController;
import interiores.business.controllers.UnaryConstraintController;
import interiores.core.Application;
import interiores.core.Debug;
import interiores.core.data.JAXBDataController;
import interiores.core.presentation.SwingController;
import interiores.core.presentation.TerminalController;
import interiores.data.MappedDataController;
import interiores.presentation.swing.views.MainAppFrame;

/**
 * Main application class.
 * @author hector
 */
public class Interiores
{

    /**
     * Main application method.
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // Gimme info of the errors, yo!
        Debug.enable();
        
        // Application for wiring
        Application app = new Application();
        
        // Application dependencies
        JAXBDataController dataController = new MappedDataController();
        TerminalController terminal = new TerminalController("interiores.presentation.terminal.commands");
        SwingController swingController = new SwingController(MainAppFrame.class);
        
        // Terminal welcome message
        terminal.setWelcomeMessage("Welcome to Interior design! Use 'help' to see the available commands.");
        
        // Persistance layer
        app.setDataController(dataController);
        
        // Presentation layer
        app.addPresentation(swingController);
        app.addPresentation(terminal);
        
        // Business layer
        app.addBusiness(RoomController.class);
        app.addBusiness(UnaryConstraintController.class);
        app.addBusiness(BinaryConstraintController.class);
        app.addBusiness(DesignController.class);
        app.addBusiness(FurnitureModelController.class);
        app.addBusiness(FurnitureTypeController.class);
        app.addBusiness(RoomTypeController.class);
        app.addBusiness(FurnitureTypesCatalogController.class);
        app.addBusiness(RoomTypesCatalogController.class);
        app.addBusiness(FixedElementController.class);
        
        // Run, run, run!!!
        app.init();
    }
}
