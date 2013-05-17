package interiores.business.events.backtracking;

import interiores.business.models.backtracking.FurnitureVariable;
import interiores.core.Event;

/**
 *
 * @author hector
 */
public class ActualVariableSetEvent
    implements Event
{
    private FurnitureVariable variable;
    
    public ActualVariableSetEvent(FurnitureVariable variable)
    {
        this.variable = variable;
    }
    
    public String getVariableName()
    {
        return variable.getID();
    }
}
