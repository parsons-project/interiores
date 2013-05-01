package interiores.presentation.terminal;

import horarios.shared.ElementNotFoundException;
import interiores.business.controllers.FurnitureTypeController;
import interiores.business.exceptions.DefaultCatalogOverwriteException;
import interiores.business.exceptions.ElementNotFoundBusinessException;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.core.business.BusinessException;
import java.util.Collection;

/**
 *
 * @author hector
 */
public class FurnitureTypeCommands
    extends CatalogElementCommands
{
    private FurnitureTypeController fTypeController;
    
    public FurnitureTypeCommands(FurnitureTypeController fTypeController) {
        super(fTypeController, "furniture types");
        
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
    
    public void select() throws ElementNotFoundBusinessException, NoRoomCreatedException, ElementNotFoundException {
        String name = readString("Enter the name of the furniture type you want to select");
        fTypeController.select(name);
    }
    
    public void unselect()
            throws NoRoomCreatedException, BusinessException, ElementNotFoundException
    {
        selected();
        
        String name = readString("Please, enter the name of the furniture type you want to unselect");
        fTypeController.unselect(name);
    }
    
    public void selected()
            throws NoRoomCreatedException
    {
        Collection types = fTypeController.getRoomFurniture();
        
        if(types.isEmpty())
            println("You have not selected any furniture yet");
        else {
            println("These are the furniture types you want in your room: ");
            print(types);
        }
    }
}
