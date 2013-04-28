package interiores.core.presentation.terminal;

import java.io.IOException;
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
    
    public String readString(String question) throws IOException
    {
        if(! iostream.hasNext())
            iostream.println(question);
        
        return iostream.readString();
    }
    
    public int readInt(String question) throws IOException
    {
        if(! iostream.hasNextInt())
            iostream.println(question);
        
        return iostream.readInt();
    }
    
    public void println(String line) {
        iostream.println(line);
    }
    
    public void print(Collection<String> collection) {
        for(String s : collection)
            println(s);
    }
}
