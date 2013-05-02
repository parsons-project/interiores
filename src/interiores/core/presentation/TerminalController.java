package interiores.core.presentation;

import interiores.core.Debug;
import interiores.core.Utils;
import interiores.core.business.BusinessController;
import interiores.core.business.BusinessException;
import interiores.core.presentation.terminal.CommandGroup;
import interiores.core.presentation.terminal.IOStream;
import interiores.core.presentation.terminal.annotation.CommandSubject;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.bind.JAXBException;

/**
 * Represents a terminal command line tool presentation controller.
 * @author hector
 */
public class TerminalController extends PresentationController
{
    /**
     * The padding for the commands name when showing the help.
     */
    private static final int HELP_PADDING = 20;
    
    /**
     * The package where the commands are located
     */
    private String commandsPackage;
    
    /**
     * The input/output stream that the terminal uses
     */
    private IOStream iostream;
    
    /**
     * Map of commands identified by subject name
     */
    private Map<String, CommandGroup> commands;
    
    /**
     * Map of available shortcuts for the commands subjects
     */
    private Map<String, String> shortcuts;
    
    /**
     * Constructs a new TerminalController with the default System input/output.
     * @param commandsPackage The package where the commands are located
     */
    public TerminalController(String commandsPackage)
    {
        this.commandsPackage = commandsPackage;
        iostream = new IOStream(System.in, System.out);
        commands = new TreeMap();
        shortcuts = new HashMap();
    }
    
    /**
     * Initializes and runs the terminal.
     */
    @Override
    public void init()
    {
        String line = iostream.readLine();
        
        while(line != null && !line.startsWith("quit"))
        {
            // Set subcommand prompt
            iostream.setPrompt('#');
            exec(line);
            // Set command prompt
            iostream.setPrompt('>');
            line = iostream.readLine();
        }
    }
    
    /**
     * Recieves events and, in debug mode, prints the information received.
     * @param name Name of the event
     * @param data Data related with the event
     */
    @Override
    public void notify(String name, Map<String, ?> data)
    {
        if(Debug.isEnabled()) {
            System.out.println(name);
        
            for(Map.Entry<String, ?> e : data.entrySet())
                iostream.println(e.getKey() + ": " + e.getValue().toString());
        }
    }
    
    /**
     * Adds a business controller on top of the terminal creating a command subject of the same name and
     * associating the business controller to it.
     * These controllers are injected into the commands of the terminal using reflection.
     * @param name Name of the business controller
     * @param controller Business controller to add
     */
    @Override
    public void addBusinessController(String name, BusinessController controller)
    {
        try
        {
            Class comgroupClass = Class.forName(commandsPackage + "." + Utils.capitalize(name) +
                    "Commands");
            
            if(!comgroupClass.isAnnotationPresent(CommandSubject.class))
                throw new Exception("There is no CommandSubject annotation in the " + name + " command group.");
            
            CommandSubject cSubject = (CommandSubject) comgroupClass.getAnnotation(CommandSubject.class);
            
            String commandName = cSubject.name();
            
            if(! name.equals(commandName))
                shortcuts.put(name, commandName);
            
            CommandGroup comgroup = (CommandGroup) comgroupClass.getConstructor(
                    controller.getClass()).newInstance(controller);
            
            comgroup.setIOStream(iostream);
            commands.put(commandName, comgroup);
            
            super.addBusinessController(name, controller);
        }
        catch(Exception e)
        {
            if(Debug.isEnabled())
                e.printStackTrace();
        }
    }
    
    /**
     * Executes a command line.
     * @param line The command line.
     */
    private void exec(String line)
    {
        try
        {
            iostream.putIntoInputBuffer(line);
            
            String action, method;
            action = method = iostream.readString();
            
            if(action.equals("help") && !iostream.hasNext()) {
                showHelp();
                return;
            }
            
            if(isReserved(action))
                method = "_" + action;
            
            String shortcut = iostream.readString();
            String subject = getSubject(shortcut);
            
            Debug.println("Action is " + action + " on subject " + subject);
            
            if(! commands.containsKey(subject))
                throw new Exception("There is no subject known as " + subject);
            
            CommandGroup comgroup = commands.get(subject);
            Class comgroupClass = comgroup.getClass();
            
            try {
                comgroupClass.getMethod(method).invoke(comgroup);
            }
            catch(InvocationTargetException e) {
                throw e.getCause();
            }
        }
        catch(BusinessException e) {
            iostream.println("[Business error] " + e.getMessage());
        }
        catch(JAXBException e) {
            iostream.println("[Storage error] " + e.getMessage());
            
            if(Debug.isEnabled())
                e.printStackTrace();
        }
        catch(Throwable e) {
            if(Debug.isEnabled())
                e.printStackTrace();
        }
    }
    
    public void showHelp() {
        iostream.println("Available commands:");
        
        for(CommandGroup command : commands.values()) {
            Class commandClass = command.getClass();
            CommandSubject cSubject = (CommandSubject) commandClass.getAnnotation(CommandSubject.class);
            
            iostream.println("    " + Utils.padRight(cSubject.name(), HELP_PADDING) + cSubject.description());
        }
    }
    
    /**
     * Returns the full subject of the given shortcut, if the shortcut does not exist it returns the
     * shortcut itself.
     * @param shortcut The shortcut of the subject to get
     * @return The full subject
     */
    private String getSubject(String shortcut) {
        if(! shortcuts.containsKey(shortcut))
            return shortcut;
        
        return shortcuts.get(shortcut);
    }
    
    /**
     * Tells whether a command is a reserved word for a Java method.
     * @param s Command name
     * @return True if s is a reserved word, false otherwise
     */
    private boolean isReserved(String s)
    {
        return (s.equals("new"));
    }
}
