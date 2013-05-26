package interiores.presentation.swing.views.map.doors;

import interiores.business.models.Orientation;
import interiores.business.models.room.FurnitureModel;
import interiores.presentation.swing.views.map.MapDoor;

/**
 *
 * @author hector
 */
abstract public class DoorOpenOriented
    extends MapDoor
{
    public DoorOpenOriented(String name, FurnitureModel model) {
        super(name, model);
    }
    
    protected void reposition(Orientation reposX, Orientation reposY) {
        int dx = 0;
        int dy = 0;
        
        if(orientation == reposX) {
            dx += size - rectangle.getWidth();
        }
        
        else if(orientation == reposY)
            dy += size - rectangle.getHeight();
            
        rectangle.translate(dx, dy);
    }
}
