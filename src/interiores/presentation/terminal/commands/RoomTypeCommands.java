package interiores.presentation.terminal.commands;

import interiores.business.controllers.RoomTypeController;
import interiores.core.presentation.terminal.annotation.Command;
import interiores.core.presentation.terminal.annotation.CommandSubject;
import interiores.presentation.terminal.commands.abstracted.CatalogElementCommands;
import java.util.Collection;

/**
 *
 * @author hector
 */
@CommandSubject(name = "rt", description = "Room types related commands")
public class RoomTypeCommands
    extends CatalogElementCommands
{
    private RoomTypeController rTypeController;
    
    public RoomTypeCommands(RoomTypeController rTypeController) {
        super(rTypeController, "room types");
        
        this.rTypeController = rTypeController;
    }
    
    @Command("Add a room type to the catalog")
    public void add() {
        String name = readString("Enter the name of the room type you want to add:");
        int width = readInt("Enter the minimum width and depth of this type of room");
        int depth = readInt("");
        
        rTypeController.add(name,width,depth);
    }
    
    @Command("Mark a type of furniture as mandatory or forbidden for a room type")
    public void put() {
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
    
    @Command("Release a type of furniture as mandatory or forbidden for a room type")
    public void release() {
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
    
    @Command("List the mandatory and forbidden types of furniture of a room type")
    public void types() {
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
