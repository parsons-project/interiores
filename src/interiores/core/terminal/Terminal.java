package interiores.core.terminal;

import interiores.core.ViewLoader;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hector0193
 */
public class Terminal
{
    private Map<String, CommandGroup> commands;
    private ViewLoader vloader;
    
    public Terminal()
    {
        commands = new HashMap<String, CommandGroup>();
        vloader = null;
    }
    
    public void init()
    {
        if(hasGui())
            vloader.init();
    }
    
    public void addCommands(String subject, CommandGroup commands)
    {
        this.commands.put(subject, commands);
    }
    
    public void setViewLoader(ViewLoader vloader)
    {
        this.vloader = vloader;
    }
    
    public boolean hasGui()
    {
        return vloader != null;
    }
    
    public void exec(String line)
    {
        try
        {
            // Load GUI View
            // Add line to scanner
            // Execute command
            // If fails:
            // - Show GUI View, if not shown
            // - Throw error, if shown (view is not working correctly)
            // Unload GUI View
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
