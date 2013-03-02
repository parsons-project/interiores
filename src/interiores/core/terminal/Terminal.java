package interiores.core.terminal;

import interiores.core.ViewLoader;
import interiores.core.mvc.View;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hector0193
 */
public class Terminal
{
    private Input input;
    private Map<String, CommandGroup> commands;
    private ViewLoader vloader;
    
    public Terminal()
    {
        input = new Input();
        commands = new HashMap<String, CommandGroup>();
        vloader = null;
    }
    
    public void init()
    {
        if(hasGui())
            vloader.init();
        else
            exec(input.readLine());
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
            input.set(line);
            String action = input.getString();
            String subject = input.getString();
            
            CommandGroup comgroup = commands.get(subject);
            
            if(hasGui() && !vloader.isLoaded(name))
            {
                vloader.load(name);
                
                View view = vloader.get(name);
                comgroup.addListener(view);
            }
                
            
            
            Class comgroupClass = comgroup.getClass();
            
            try
            {
                comgroupClass.getMethod(action).invoke(comgroup);
            }
            catch(Exception e)
            {
                
            }
            
            if(hasGui())
                vloader.unload(name);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
