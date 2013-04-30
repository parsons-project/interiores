package interiores.core.presentation.terminal;

import java.util.Collection;

/**
 *
 * @author hector
 */
abstract public class CommandGroup
{
    private IOStream iostream;
    
    public void setIOStream(IOStream iostream)
    {
        this.iostream = iostream;
    }
    
    public String readString(String question)
    {
        return iostream.readString(question);
    }
    
    public Collection<String> readStrings(String question) {
        return iostream.readStrings(question);
    }
    
    public int readInt(String question)
    {       
        return iostream.readInt(question);
    }
    
    public void println(String line) {
        iostream.println(line);
    }
    
    public void print(Collection<?> collection) {
        for(Object o : collection)
            println(o.toString());
    }
}
