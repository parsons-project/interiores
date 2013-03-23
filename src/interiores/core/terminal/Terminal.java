package interiores.core.terminal;

import interiores.core.Observer;
import interiores.core.Utils;
import interiores.core.business.Controller;
import interiores.core.presentation.ViewLoader;
import interiores.core.presentation.View;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hector0193
 */
public class Terminal implements Observer
{
    private IOStream iostream;
    private Map<String, CommandGroup> commands;
    private ViewLoader vloader;
    
    public Terminal()
    {
        iostream = new IOStream();
        commands = new HashMap<String, CommandGroup>();
        vloader = null;
    }
    
    public void init() throws Exception
    {
        if(hasGui())
        {
            loadView(null, "main", "app");
            // Ignore System output with GUI
            iostream.setOutputStream(new PrintStream("/dev/null"));
        }
        else
        {
            // Terminal mode
            iostream.setInputStream(System.in);
            iostream.setOutputStream(System.out);
            
            String line = iostream.readLine();
            
            while(line != null && !line.startsWith("quit"))
            {
                exec(line);
                line = iostream.readLine();
            }
        }
    }
    
    public void addCommands(String subject, CommandGroup commands, Controller controller)
    {
        controller.registerObserver(this);
        commands.setController(controller);
        commands.setIOStream(iostream);
        this.commands.put(subject, commands);
    }
    
    public void notify(String name, Map<String, Object> data)
    {
        System.out.println(name);
        
        for(Map.Entry<String, Object> e : data.entrySet())
            System.out.println(e.getKey() + ": " + e.getValue().toString());
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
            iostream.setInputBuffer(line);
            
            String action, method;
            action = method = iostream.readString();
            
            if(isReserved(action))
                method = "_" + action;
            
            String subject = iostream.readString();
            
            System.out.println("Action is " + action + " on subject " + subject);
            
            if(! commands.containsKey(subject))
                throw new Exception("There is no subject known as " + subject);
            
            CommandGroup comgroup = commands.get(subject);
            Class comgroupClass = comgroup.getClass();
            
            try
            {
                try
                {
                    comgroupClass.getMethod(method).invoke(comgroup);
                }
                catch(InvocationTargetException e)
                {
                    throw e.getCause();
                }
            }
            catch(IOException e)
            {
                if(hasGui())
                    loadView(comgroup, action, subject);
                else
                    throw e;
            }
            catch(Throwable e)
            {
                e.printStackTrace(); // @TODO Improve exception handling
            }
        }
        catch(Exception e)
        {
            e.printStackTrace(); // @TODO Improve exception handling
        }
    }
    
    private boolean isReserved(String s)
    {
        return (s.equals("new"));
    }
    
    private void loadView(CommandGroup comgroup, String action, String subject) throws Exception
    {
        String viewName = getViewName(action, subject);
        
        boolean loaded = vloader.isLoaded(viewName);
        
        if(!loaded)
            vloader.load(viewName);
        
        View view = vloader.get(viewName);
        view.showView();
        
        if(loaded)
            throw new Exception("The view " + viewName + " may not be working correctly.");
        else
            view.setTerminal(this);
    }
    
    private String getViewName(String action, String subject)
    {
        return Utils.capitalize(action) + Utils.capitalize(subject);
    }
}
