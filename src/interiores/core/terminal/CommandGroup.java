package interiores.core.terminal;

import interiores.mvc.Controller;

/**
 *
 * @author hector
 */
abstract public class CommandGroup
{
    public CommandGroup()
    {
    
    }
    
    public String readString(String question)
    {
        return "Ye";
    }
    
    public int readInt(String question)
    {
        return 0;
    }
    
    abstract public void setController(Controller controller);
}
