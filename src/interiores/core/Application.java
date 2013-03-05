package interiores.core;

import interiores.commands.RoomCommands;
import interiores.controllers.RoomController;
import interiores.core.terminal.Terminal;
import interiores.models.Room;
import java.io.IOException;

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
    
    public void init() throws Exception
    {
        addCommands();
        
        try
        {
            terminal.init();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private void addCommands()
    {
        Room room = new Room();
        
        RoomCommands rcom = new RoomCommands();
        rcom.setController(new RoomController(room));
        
        terminal.addCommands("room", rcom);
    }

}
