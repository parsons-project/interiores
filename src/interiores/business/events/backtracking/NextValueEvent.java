package interiores.business.events.backtracking;

import interiores.business.models.Orientation;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.core.Event;
import java.awt.Point;

/**
 *
 * @author hector
 */
public class NextValueEvent
    implements Event
{
    private FurnitureValue value;
    
    public NextValueEvent(FurnitureValue value)
    {
        this.value = value;
    }
    
    public Point getPosition()
    {
        return value.getPosition();
    }
    
    public String getModelName()
    {
        return value.getModel().getName();
    }
    
    public Orientation getOrientation()
    {
        return value.getArea().getOrientation();
    }
}
