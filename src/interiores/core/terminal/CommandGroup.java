package interiores.core.terminal;

import interiores.core.business.Controller;
import java.io.IOException;

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
        iostream.println(question);
        
        return iostream.readString();
    }
    
    public int readInt(String question) throws IOException
    {
        iostream.println(question);
        
        return iostream.readInt();
    }
    
    abstract public void setController(Controller controller);
}
