package interiores.presentation.swing.views.map.doors;

import interiores.business.models.Orientation;

/**
 *
 * @author hector
 */
public class RightDoor
    extends DoorOpenOriented
{
    public RightDoor(int size) {
        super(size);
    }
    
    @Override
    public void setPosition(int x, int y, Orientation orientation) {
        super.setPosition(x, y, orientation);
        
        reposition(Orientation.N, Orientation.E);
    }
}
