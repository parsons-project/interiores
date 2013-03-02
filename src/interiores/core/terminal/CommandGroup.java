package interiores.core.terminal;

import interiores.core.mvc.Controller;
import interiores.core.mvc.Intermediary;

/**
 *
 * @author hector
 */
abstract public class CommandGroup extends Intermediary
{
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
