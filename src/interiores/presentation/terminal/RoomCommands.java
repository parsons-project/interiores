package interiores.presentation.terminal;

import interiores.business.controllers.RoomController;
import interiores.core.business.BusinessException;
import interiores.core.presentation.terminal.CommandGroup;
import javax.xml.bind.JAXBException;

/**
 *
 * @author hector
 */
public class RoomCommands extends CommandGroup
{
    private RoomController roomController;
    
    public RoomCommands(RoomController roomController) {
        super();
        
        this.roomController = roomController;
    }
    
    public void _new()
    {
        String type = readString("Especifica qué tipo de habitación quieres crear");
        int width = readInt("Introduce el ancho de la habitación en cm");
        int height = readInt("Introduce la altura de la habitación en cm");
        
        roomController.newRoom(type, width, height);
    }
    
    public void save() throws BusinessException, JAXBException
    {
        String path = readString("Especifica la ruta donde guardar la habitación");
        
        roomController.saveRoom(path);
    }
    
    public void load()
    {
        String path = readString("Especifica la ruta desde donde cargar");
        
        roomController.loadRoom(path);
    }
}
