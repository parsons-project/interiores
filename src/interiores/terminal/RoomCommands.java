package interiores.terminal;

import interiores.business.controllers.RoomController;
import interiores.core.terminal.CommandGroup;
import java.io.IOException;

/**
 *
 * @author hector
 */
public class RoomCommands extends CommandGroup
{
    public void _new() throws IOException
    {
        String type = readString("Especifica qué tipo de habitación quieres crear");
        int width = readInt("Introduce el ancho de la habitación en cm");
        int height = readInt("Introduce la altura de la habitación en cm");
        
        RoomController c = (RoomController) terminal.getBusinessController("room");
        c.newRoom(type, width, height);
    }
}
