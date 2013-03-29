package interiores.core;

import interiores.core.business.Controller;
import interiores.core.presentation.PresentationController;
import interiores.core.presentation.ViewLoader;
import interiores.core.terminal.CommandGroup;
import interiores.core.terminal.Terminal;
import interiores.data.DataController;

/**
 *
 * @author hector
 */
public class Application
{
    private String appPkg;
    private DataController data;
    private Terminal terminal;
    
    public Application(String appPkg)
    {
        this.appPkg = appPkg;
        data = new DataController();
        terminal = new Terminal();
    }
    
    public void enableGUI()
    {
        ViewLoader vloader = new ViewLoader(appPkg + ".presentation.views");
        PresentationController presentation = new PresentationController(vloader, terminal);
        
        terminal.setPresentation(presentation);
    }
    
    public void init() throws Exception
    {   
        try
        {
            terminal.init();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void addCommandGroup(String name)
    {
        try
        {
            Class comgroupClass = Class.forName(appPkg + ".terminal." + Utils.capitalize(name) + "Commands");
            Class controllerClass = Class.forName(appPkg + ".business.controllers." + Utils.capitalize(name) +
                    "Controller");
            
            addCommandGroup(name,
                    (CommandGroup) comgroupClass.newInstance(),
                    (Controller)   controllerClass.getConstructor(DataController.class).newInstance(data));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void addCommandGroup(String name, CommandGroup comgroup, Controller controller)
    {
        terminal.addCommandGroup(name, comgroup, controller);
    }
}
