package interiores.core.terminal;

import interiores.core.mvc.Controller;
import interiores.core.mvc.Intermediary;
import java.io.IOException;

/**
 *
 * @author hector
 */
abstract public class CommandGroup extends Intermediary
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
