package interiores.business.events.backtracking;

import interiores.business.models.backtracking.InterioresVariable;
import interiores.core.Event;

/**
 *
 * @author hector
 */
public class ActualVariableSetEvent
    implements Event
{
    private InterioresVariable variable;
    
    public ActualVariableSetEvent(InterioresVariable variable)
    {
        this.variable = variable;
    }
    
    public String getVariableName()
    {
        return variable.getID();
    }
}
