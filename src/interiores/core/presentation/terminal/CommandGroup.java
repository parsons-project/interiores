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
        if(! iostream.hasNext())
            iostream.println(question);
        
        return iostream.readString();
    }
    
    public int readInt(String question)
    {
        if(! iostream.hasNextInt())
            iostream.println(question);
        
        return iostream.readInt();
    }
    
    public void println(String line) {
        iostream.println(line);
    }
    
    public void print(Collection collection) {
        for(Object s : collection)
            println(s.toString());
    }
}
