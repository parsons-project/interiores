package interiores.business.events.backtracking;

import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.core.Event;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

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
    
    public OrientedRectangle getArea()
    {
        return value.getArea();
    }
    
    public Rectangle getWholeArea() {
        return value.getWholeArea();
    }
    
    public String getModelName()
    {
        return value.getModel().getName();
    }
   
    public Color getModelColor()
    {
        return value.getModel().getColor();
    }
    
    public Orientation getOrientation()
    {
        return value.getArea().getOrientation();
    }
}
