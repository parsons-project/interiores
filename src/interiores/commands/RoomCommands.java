package interiores.commands;

import interiores.controllers.RoomController;
import interiores.core.mvc.Controller;
import interiores.core.terminal.CommandGroup;
import java.io.IOException;

/**
 *
 * @author hector
 */
public class RoomCommands extends CommandGroup
{
    private RoomController controller;
    
    public void _new() throws IOException
    {
        String type = readString("Especifica qué tipo de habitación quieres crear");
        int width = readInt("Introduce el ancho de la habitación en cm");
        int height = readInt("Introduce la altura de la habitación en cm");
        
        controller.newRoom(type, width, height);
    }
    
    @Override
    public void setController(Controller controller)
    {
        this.controller = (RoomController) controller;
        controller.addListener(this);
    }
}
