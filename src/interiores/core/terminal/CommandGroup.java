package interiores.core.terminal;

import interiores.core.presentation.TerminalController;
import java.io.IOException;

/**
 *
 * @author hector
 */
abstract public class CommandGroup
{
    private IOStream iostream;
    protected TerminalController terminal;
    
    public void setTerminal(TerminalController terminal)
    {
        this.terminal = terminal;
    }
    
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
}
