package interiores.business.events.backtracking;

import interiores.business.models.backtracking.FurnitureValue;

/**
 *
 * @author hector
 */
public class ValueAssignedEvent
    extends NextValueEvent
{
    public ValueAssignedEvent(FurnitureValue value) {
        super(value);
    }
    
    
}
