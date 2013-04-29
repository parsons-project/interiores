package interiores.presentation.terminal;

import interiores.business.controllers.FurnitureTypeController;
import interiores.business.models.FurnitureType;
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
    
    public void add() {
        String name = readString("Enter the name of the furniture type you want to add");
        
        int minWidth = readInt("Enter the minimum width of the type");
        int minDepth = readInt("Enter the minimum depth of the type");
        
        int maxWidth = readInt("Enter the maximum width of the type");
        int maxDepth = readInt("Enter the maximum depth of the type");
        
        fTypeController.add(name, minWidth, minDepth, maxWidth, maxDepth);
    }
    
    public void list() {
        Collection<FurnitureType> types = fTypeController.getCatalogObjects();
        
        println("Listing furniture types in the actual catalog:");
        
        for(FurnitureType type : types)
            println(type.toString());
    }
}
