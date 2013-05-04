package interiores.core.presentation.terminal;

import interiores.core.Utils;
import interiores.core.presentation.terminal.annotation.Command;
import interiores.core.presentation.terminal.annotation.CommandSubject;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Represents a group of terminal commands under a common subject.
 * Provides some handy methods to read/print data from/to the user.
 * @author hector
 */
abstract public class CommandGroup
{
    /**
     * Padding of the help command
     */
    private static final int HELP_PADDING = 20;
    
    /**
     * The input/output streams to read/print data from/to the user
     */
    private IOStream iostream;
    
    /**
     * Sets the input/output streams.
     * @param iostream The input/output stream to set
     */
    public void setIOStream(IOStream iostream)
    {
        this.iostream = iostream;
    }
    
    /**
     * Reads a string from the user asking a question.
     * @param question The question to ask
     * @return The read string
     */
    public String readString(String question)
    {
        return iostream.readString(question);
    }
    
    /**
     * Reads all the strings left in the input buffer asking a question.
     * @param question The question to ask
     * @return The read strings
     */
    public Collection<String> readStrings(String question) {
        return iostream.readStrings(question);
    }
    
    /**
     * Reads a choice from the input asking a question.
     * If the answer of the user it's not a valid choice, the first choice parameter is taken as default.
     * @param question The question to ask
     * @param choices The valid choices
     * @return The choice that the user has selected
     */
    public String readChoice(String question, String ... choices) {
        List<String> list = Arrays.asList(choices);
        
        String available = "Available choices are: ";
        
        for(int i = 0; i < list.size(); ++i) {
            if(i != 0) available += ", ";
            available += list.get(i);
        }
        
        String choice = readString(question + " (" + available + ")");
        
        if(! list.contains(choice))
            choice = list.get(0);
        
        return choice;
    }
    
    /**
     * Reads an integer from the input asking a question.
     * @param question The question to ask
     * @return The read integer
     */
    public int readInt(String question)
    {       
        return iostream.readInt(question);
    }
    
    /**
     * Reads a float from the input asking a question.
     * @param question The question to ask
     * @return The read float
     */
    public float readFloat(String question) {
        return iostream.readFloat(question);
    }
    
    /**
     * Prints a line to the output stream.
     * @param line Line to print
     */
    public void println(String line) {
        iostream.println(line);
    }
    
    /**
     * Prints a collection of objects.
     * @param collection The collection of objects to print
     */
    public void print(Collection<?> collection) {
        for(Object o : collection)
            println(o.toString());
    }
    
    @Command("Obtain detailed command information")
    public void help() {
        Class commandClass = getClass();
        CommandSubject cSubject = (CommandSubject) commandClass.getAnnotation(CommandSubject.class);
        
        println("Available commands for " + cSubject.name() + ":");
        
        for(Method method : commandClass.getMethods()) {
            if(! method.isAnnotationPresent(Command.class))
                continue;
            
            Command commandAnnotation = method.getAnnotation(Command.class);
            
            String name = method.getName();
            
            if(name.startsWith("_"))
                name = name.substring(1);
            
            iostream.println("    " + Utils.padRight(name + " " + cSubject.name(), HELP_PADDING)
                    + commandAnnotation.value());
        }
    }
}
