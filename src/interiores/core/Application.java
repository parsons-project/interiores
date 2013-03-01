package interiores.core;

import interiores.commands.RoomCommands;
import interiores.controllers.RoomController;
import interiores.core.terminal.Terminal;
import interiores.models.Room;

/**
 *
 * @author hector
 */
public class Application
{
    private Terminal terminal;
    
    public Application(Terminal terminal)
    {
        this.terminal = terminal;
    }
    
    public void init()
    {
        addCommands();
        
        terminal.init();
    }
    
    private void addCommands()
    {
        Room room = new Room();
        
        RoomCommands rcom = new RoomCommands();
        rcom.setController(new RoomController(room));
        
        terminal.addCommands("room", rcom);
    }

}
