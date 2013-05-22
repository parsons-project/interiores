package interiores.presentation.terminal.commands;

import interiores.business.controllers.RoomController;
import interiores.core.presentation.terminal.AdvancedCommandGroup;
import interiores.core.presentation.terminal.annotation.Command;
import interiores.core.presentation.terminal.annotation.CommandSubject;
import javax.xml.bind.JAXBException;

/**
 *
 * @author hector
 */
@CommandSubject(name = "room", description = "Room related commands")
public class RoomCommands extends AdvancedCommandGroup
{
    private RoomController roomController;
    
    public RoomCommands(RoomController roomController) {
        super();
        
        this.roomController = roomController;
    }
    
    @Command("Create a new room")
    public void _new() {
        String type = readString("Enter the name of the room type you want to create:");
        int width = readInt("Enter the width of the room in centimeters:");
        int depth = readInt("Enter the depth of the room in centimeters:");
        
        roomController.create(type, width, depth);
    }
    
    @Command("Save the current room")
    public void save()
            throws JAXBException
    {
        String path = readString("Especifica la ruta donde guardar la habitación");
        
        roomController.save(path);
    }
    
    @Command("Load a room")
    public void load()
            throws JAXBException
    {
        String path = readString("Especifica la ruta desde donde cargar");
        
        roomController.load(path);
    }
}
