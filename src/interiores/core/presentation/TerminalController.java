package interiores.core.presentation;

import interiores.core.Debug;
import interiores.core.Utils;
import interiores.core.business.BusinessController;
import interiores.core.business.BusinessException;
import interiores.core.presentation.terminal.CommandGroup;
import interiores.core.presentation.terminal.IOStream;
import interiores.core.Options;
import interiores.core.presentation.terminal.annotation.Command;
import interiores.core.presentation.terminal.annotation.CommandOptions;
import interiores.core.presentation.terminal.annotation.CommandSubject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
     * The padding for the commands name when showing the help
     */
    private static final int HELP_PADDING = 20;
    
    /**
     * The options pattern
     */
    private static final String OPTIONS_PATTERN = "--([a-z]+)";
    
    /**
     * The package where the commands are located
     */
    private String commandsPackage;
    
    /**
     * The input/output stream that the terminal uses
     */
    private IOStream iostream;
    
    /**
     * Map of command groups identified by subject name
     */
    private Map<String, CommandGroup> commandGroups;
    
    /**
     * Map of maps: subject -> command -> method
     */
    private Map<String, Map<String, Method>> commands;
    
    /**
     * Map of available shortcuts for the commands subjects
     */
    private Map<String, String> shortcuts;
    
    /**
     * The welcoming message that the terminal shows on initialization
     */
    private String welcomeMsg;
    
    /**
     * Constructs a new TerminalController with the default System input/output.
     * @param commandsPackage The package where the commands are located
     */
    public TerminalController(String commandsPackage)
    {
        this.commandsPackage = commandsPackage;
        iostream = new IOStream(System.in, System.out);
        commandGroups = new TreeMap();
        commands = new HashMap();
        shortcuts = new HashMap();
    }
    
    public void setWelcomeMessage(String welcomeMsg) {
        this.welcomeMsg = welcomeMsg;
    }
    
    /**
     * Initializes and runs the terminal.
     */
    @Override
    public void init()
    {
        iostream.println(welcomeMsg);
        
        String line = iostream.readLine();
        
        while(line != null)
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
            Class comGroupClass = Class.forName(commandsPackage + "." + Utils.capitalize(name) +
                    "Commands");
            
            if(!comGroupClass.isAnnotationPresent(CommandSubject.class))
                throw new Exception("There is no CommandSubject annotation in the " + name + " command "
                        + "group.");
            
            CommandSubject cSubject = (CommandSubject) comGroupClass.getAnnotation(CommandSubject.class);
            String comGroupName = cSubject.name();
            
            if(! name.equals(comGroupName))
                shortcuts.put(name, comGroupName);
            
            addCommandGroup(comGroupName, comGroupClass, controller);
            super.addBusinessController(name, controller);
        }
        catch(Exception e)
        {
            if(Debug.isEnabled())
                e.printStackTrace();
        }
    }
    
    private void addCommandGroup(String comGroupName, Class comGroupClass, BusinessController controller)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException
    {
        CommandGroup commandGroup = (CommandGroup) comGroupClass.getConstructor(
                controller.getClass()).newInstance(controller);
        
        commandGroup.setIOStream(iostream);
        commandGroups.put(comGroupName, commandGroup);
        
        commands.put(comGroupName, new HashMap());
        
        for(Method method : comGroupClass.getMethods())
            if(method.isAnnotationPresent(Command.class))
                commands.get(comGroupName).put(method.getName(), method);
    }
    
    /**
     * Executes a command line.
     * @param line The command line.
     */
    private void exec(String line)
    {
        iostream.putIntoInputBuffer(line);
        
        String actionName, commandName;
        actionName = commandName = iostream.readString();
        
        if(actionName.equals("quit")) {
            quit();
            return;
        }
        
        if(actionName.equals("help") && !iostream.hasNext()) {
            showHelp();
            return;
        }
        
        if(isReserved(actionName))
            commandName = "_" + actionName;
        
        String shortcut = iostream.readString();
        String subjectName = getSubject(shortcut);
        
        Debug.println("Action is " + actionName + " on subject " + subjectName);
        
        try
        {         
            exec(subjectName, commandName, actionName);
        }
        catch(BusinessException e) {
            iostream.println("[Business error] " + e.getMessage());
        }
        catch(JAXBException e) {
            iostream.println("[Storage error] " + e.getMessage());
            
            if(Debug.isEnabled())
                e.printStackTrace();
        }
        catch(NoSuchMethodException e) {
            iostream.println("[Terminal error] Command not found.");
        }
        catch(Throwable e) {
            if(Debug.isEnabled())
                e.printStackTrace();
        }
    }
    
    private void exec(String subjectName, String commandName, String actionName)
            throws Throwable
    {
        if(! commandGroups.containsKey(subjectName))
            throw new BusinessException("There is no subject known as " + subjectName);
        
        CommandGroup commandGroup = commandGroups.get(subjectName);
        Map<String, Method> subjectCommands = commands.get(subjectName);
        
        if(! subjectCommands.containsKey(commandName))
            throw new BusinessException(actionName + " command not found on subject " + subjectName + ".");
            
        Method command = subjectCommands.get(commandName);
        Options commandOptions = getOptions();
        
        try {
            if(command.isAnnotationPresent(CommandOptions.class)) {
                CommandOptions optionsAnnotation = command.getAnnotation(CommandOptions.class);
                commandOptions.disableIfNotPresent(optionsAnnotation.value());
                
                command.invoke(commandGroup, commandOptions);
            }
            else {
                if(commandOptions.hasOptions())
                    Debug.println("This command does not accept any option.");
                
                command.invoke(commandGroup);
            }
        }
        catch(InvocationTargetException e) {
            throw e.getCause();
        }
    }
    
    private Options getOptions() {
        Options options = new Options();
        
        while(iostream.hasNext(OPTIONS_PATTERN)) {
            String option = iostream.readString().substring(2);
            options.enable(option);
        }
        
        return options;
    }
    
    public void showHelp() {
        iostream.println("Available commands:");
        
        for(CommandGroup command : commandGroups.values()) {
            Class commandClass = command.getClass();
            CommandSubject cSubject = (CommandSubject) commandClass.getAnnotation(CommandSubject.class);
            
            iostream.println("    " + Utils.padRight(cSubject.name(), HELP_PADDING) + cSubject.description());
        }
        
        iostream.println("    " + Utils.padRight("quit", HELP_PADDING) + "Closes the application");
        iostream.println("Use 'help [command]' to show more information about the command.");
    }
    
    public void quit() {
        boolean isConfirmed = iostream.readBoolean("Are you sure do you really want to quit?");
        
        if(isConfirmed)
            System.exit(0);
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
