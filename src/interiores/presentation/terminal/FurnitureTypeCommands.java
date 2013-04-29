package interiores.presentation.terminal;

import horarios.shared.ElementNotFoundException;
import interiores.business.controllers.FurnitureTypeController;
import interiores.business.exceptions.DefaultCatalogOverwriteException;
import interiores.core.business.BusinessException;
import interiores.core.presentation.terminal.CommandGroup;
import java.util.Collection;

/**
 *
 * @author hector
 */
public class FurnitureTypeCommands
    extends CommandGroup
{
    private FurnitureTypeController fTypeController;
    
    public FurnitureTypeCommands(FurnitureTypeController fTypeController) {
        this.fTypeController = fTypeController;
    }
    
    public void add() throws DefaultCatalogOverwriteException {
        String name = readString("Enter the name of the furniture type you want to add");
        
        int minWidth = readInt("Enter the minimum width of the type");
        int maxWidth = readInt("Enter the maximum width of the type");
        
        int minDepth = readInt("Enter the minimum depth of the type");
        int maxDepth = readInt("Enter the maximum depth of the type");
        
        fTypeController.add(name, minWidth, maxWidth, minDepth, maxDepth);
    }
    
    public void list() {
        Collection types = fTypeController.getCatalogObjects();
        
        println("Listing furniture types of the catalog: " + fTypeController.getCatalogName());
        
        print(types);
        
        if(types.isEmpty())
            println("The catalog is empty.");
    }
    
    public void select() throws ElementNotFoundException {
        String name = readString("Enter the name of the furniture type you want to select");
        fTypeController.select(name);
    }
    
    public void unselect() throws BusinessException, ElementNotFoundException {
        println("These are the furniture types you want in your room: ");
        Collection types = fTypeController.getRoomTypes();
        print(types);
        
        String name = readString("Please, enter the name of the furniture type you want to select");
        fTypeController.unselect(name);
    }
}
