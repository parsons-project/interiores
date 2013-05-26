package interiores.presentation.swing.views.map.doors;

import interiores.business.models.Orientation;
import interiores.business.models.room.FurnitureModel;
import java.awt.Point;

/**
 *
 * @author hector
 */
public class RightDoor
    extends DoorOpenOriented
{
    public RightDoor(String name, FurnitureModel model, Point position, Orientation orientation) {
        super(name, model);
        
        setPosition(position.x, position.y, orientation);
    }
    
    @Override
    public final void setPosition(int x, int y, Orientation orientation) {
        super.setPosition(x, y, orientation);
        
        reposition(Orientation.S, Orientation.W);
    }
}
