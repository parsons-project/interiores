package interiores.core.presentation.terminal;

import interiores.core.Debug;
import interiores.core.Options;
import interiores.core.presentation.terminal.annotation.CommandOptions;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author hector
 */
public class CommandAction
{
    /**
     * The options pattern of this command
     */
    private static final String OPTIONS_PATTERN = "--([a-z]+)";
    
    /**
     * The name of this command
     */
    private String name;
      
    /**
     * The description of this command
     */
    private String description;
    
    /**
     * The command group of this command
     */
    private CommandGroup commandGroup;
    
    /**
     * The options that this command accepts
     */
    private String[] options;
    
    /**
     * The action that this command represents
     */
    private Method action;
    
    public CommandAction(CommandGroup commandGroup, Method action, String description)
    {
        name = action.getName();
        
        if(name.startsWith("_"))
            name = name.substring(1);
        
        this.action = action;
        this.description = description;
        this.commandGroup = commandGroup;
        
        if(action.isAnnotationPresent(CommandOptions.class))
            options = action.getAnnotation(CommandOptions.class).value();
        else
            options = new String[]{};
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public String getOptionsPattern()
    {
        return OPTIONS_PATTERN;
    }
    
    public String getOptionName(String match)
    {
        return match.substring(2);
    }
    
    public String[] getOptions()
    {
        return options;
    }
    
    public void exec(Options currentOptions)
            throws Throwable
    {
        try {
            if(options.length == 0) {
                if(currentOptions.hasOptions())
                    Debug.println("This command does not accept options.");
                
                action.invoke(commandGroup);
            }
            else
                action.invoke(commandGroup, currentOptions);
        }
        catch(InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
