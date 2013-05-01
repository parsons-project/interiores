package interiores.presentation.terminal;

import interiores.business.controllers.RoomTypeController;
import interiores.business.exceptions.DefaultCatalogOverwriteException;
import interiores.business.exceptions.ElementNotFoundBusinessException;
import java.util.Collection;

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
    
    public void put()
            throws DefaultCatalogOverwriteException, ElementNotFoundBusinessException
    {
        String roomTypeName = readString("Enter the name of the room type where you want to add the "
                + "furniture types:");
        
        String putType = readChoice("Should the furniture type be mandatory or forbidden?",
                "mandatory", "forbidden");
        
        Collection<String> fTypeNames = readStrings("Enter the name of the furniture types to put into the "
                + "room type as " + putType + ":");
        
        if(putType.equals("mandatory")) {
            for(String fTypeName : fTypeNames)
                rTypeController.addToMandatory(roomTypeName, fTypeName);
        } else {
            for(String fTypeName : fTypeNames)
                rTypeController.addToForbidden(roomTypeName, fTypeName);
        }
    }
    
    public void release()
            throws DefaultCatalogOverwriteException, ElementNotFoundBusinessException
    {
        String roomTypeName = readString("Enter the name of the room type where you want to release the "
                + "furniture types:");
        
        String relType = readChoice("Should the furniture type be released from mandatory or forbidden?",
                "mandatory", "forbidden");
        
        Collection<String> fTypeNames = readStrings("Enter the name of the " + relType + " furniture types to"
                + "release from the room type " + roomTypeName + ":");
        
        if(relType.equals("mandatory")) {
            for(String fTypeName : fTypeNames)
                rTypeController.removeFromMandatory(roomTypeName, fTypeName);
        } else {
            for(String fTypeName : fTypeNames)
                rTypeController.removeFromForbidden(roomTypeName, fTypeName);
        }
    }
    
    public void types()
            throws ElementNotFoundBusinessException
    {
        String roomTypeName = readString("Enter the name of the room type you want to list the"
                + "furniture types:");
        
        Collection<String> mandatory = rTypeController.getMandatory(roomTypeName);
        Collection<String> forbidden = rTypeController.getForbidden(roomTypeName);
        
        println("Listing furniture types of the room type: " + roomTypeName);
        println("Mandatory:");
        print(mandatory);
        
        if(mandatory.isEmpty())
            println("There are no mandatory furniture types.");
        
        println("---------------");
        
        println("Forbidden:");
        print(forbidden);
        
        if(forbidden.isEmpty())
            println("There are no forbidden furniture types.");
    }
}
