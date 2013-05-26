package interiores.presentation.swing.views.map.doors;

import interiores.business.models.Orientation;
import interiores.business.models.room.FurnitureModel;
import java.awt.Point;

/**
 *
 * @author hector
 */
public class LeftDoor
    extends DoorOpenOriented
{
    public LeftDoor(String name, FurnitureModel model, Point position, Orientation orientation) {
        super(name, model);
        
        setPosition(x, y, orientation);
    }
    
    @Override
    public final void setPosition(int x, int y, Orientation orientation) {
        super.setPosition(x, y, orientation);
        
        reposition(Orientation.N, Orientation.E);
    }
}
