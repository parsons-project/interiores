package interiores.presentation.terminal;

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
        
        getRoomController().newRoom(type, width, height);
    }
    
    public void save() throws IOException
    {
        String path = readString("Especifica la ruta donde guardar la habitación");
        
        getRoomController().saveRoom(path);
    }
    
    public void load() throws IOException
    {
        String path = readString("Especifica la ruta desde donde cargar");
        
        getRoomController().loadRoom(path);
    }
    
    public RoomController getRoomController()
    {
        return (RoomController) terminal.getBusinessController("room");
    }
}
