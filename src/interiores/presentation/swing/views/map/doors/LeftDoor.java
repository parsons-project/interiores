package interiores.presentation.swing.views.map.doors;

import interiores.business.models.Orientation;

/**
 *
 * @author hector
 */
public class LeftDoor
    extends DoorOpenOriented
{
    public LeftDoor(int size) {
        super(size);
    }
    
    @Override
    public void setPosition(int x, int y, Orientation orientation) {
        super.setPosition(x, y, orientation);
        
        reposition(Orientation.S, Orientation.W);
    }
}
