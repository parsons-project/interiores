package interiores.core.presentation.terminal;

import interiores.core.Options;
import interiores.core.Utils;
import interiores.core.business.BusinessException;
import interiores.core.presentation.terminal.annotation.Command;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hector
 */
public class CommandGroup
{
    /**
     * Padding of the help command
     */
    private static final int HELP_PADDING = 20;
    
    /**
     * The input/output streams to read/print data from/to the user
     */
    protected IOStream iostream;
    
    /**
     * The name of the command subject
     */
    private String name = "unknown";
    
    /**
     * The description of the command group
     */
    private String description = "No description";
    
    /**
     * Map of available commands
     */
    private Map<String, CommandAction> commands;
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Sets the input/output streams.
     * @param iostream The input/output stream to set
     */
    public void setIOStream(IOStream iostream)
    {
        this.iostream = iostream;
    }
    
    public void scanCommands()
    {
        commands = new HashMap();
        
        for(Method method : getClass().getMethods())
        {
            if(! method.isAnnotationPresent(Command.class))
                continue;
            
            Command commandInfo = method.getAnnotation(Command.class);
            
            CommandAction command = new CommandAction(this, method, commandInfo.value());
            commands.put(command.getName(), command);
        }
    }
    
    public void exec(String commandName)
            throws Throwable
    {
        if(! commands.containsKey(commandName))
            throw new BusinessException(commandName + " command not found on subject " + name + ".");
        
        CommandAction command = commands.get(commandName);
         
        Options currentOptions = getOptions(command);
        commands.get(commandName).exec(currentOptions);
    }
    
    public Options getOptions(CommandAction command) {
        Options options = new Options();
        
        for(String commandOption : command.getOptions())
            options.disable(commandOption);
        
        while(iostream.hasNext(command.getOptionsPattern())) {
            String option = command.getOptionName(iostream.readString());
            options.enable(option);
        }
        
        return options;
    }
    
    @Command("Obtain detailed command information")
    public void help() {
        iostream.println("Available commands for " + name + ":");
        
        for(CommandAction command : commands.values()) {
            iostream.println("    " + Utils.padRight(command.getName() + " " + name, HELP_PADDING)
                    + command.getDescription());
        }
    }
}
