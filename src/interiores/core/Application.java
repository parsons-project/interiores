package interiores.core;

import interiores.business.controllers.RoomController;
import interiores.core.presentation.ViewLoader;
import interiores.core.terminal.Terminal;
import interiores.data.DataController;
import interiores.presentation.swing.SwingLoader;
import interiores.terminal.RoomCommands;
import java.io.IOException;

/**
 *
 * @author hector
 */
public class Application
{
    private DataController data;
    private Terminal terminal;
    
    public Application(String args[])
    {
        data = new DataController();
        terminal = new Terminal();
        
        ViewLoader loader = new SwingLoader();
        terminal.setViewLoader(loader);
    }
    
    public void init() throws Exception
    {
        addCommands();
        
        try
        {
            terminal.init();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void addCommands()
    {
        terminal.addCommands("room", new RoomCommands(), new RoomController(data));
    }

}
