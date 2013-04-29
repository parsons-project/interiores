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
    
    public int readInt(String question)
    {       
        return iostream.readInt(question);
    }
    
    public void println(String line) {
        iostream.println(line);
    }
    
    public void print(Collection<String> collection) {
        for(String s : collection)
            println(s);
    }
}
