package interiores.presentation.terminal;

import interiores.business.controllers.RoomTypeController;
import interiores.business.exceptions.DefaultCatalogOverwriteException;

/**
 *
 * @author hector
 */
public class RoomTypeCommands
    extends CatalogElementCommands
{
    private RoomTypeController rTypeController;
    
    public RoomTypeCommands(RoomTypeController rTypeController) {
        super(rTypeController, "room types");
        
        this.rTypeController = rTypeController;
    }
    
    public void add()
            throws DefaultCatalogOverwriteException
    {
        String name = readString("Enter the name of the room type you want to add:");
        
        rTypeController.add(name);
    }
}
