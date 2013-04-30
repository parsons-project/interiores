package interiores.presentation.swing.views.map.doors;

import interiores.business.models.Orientation;
import interiores.presentation.swing.views.map.Door;

/**
 *
 * @author hector
 */
abstract public class DoorOpenOriented
    extends Door
{
    public DoorOpenOriented(int size) {
        super(size);
    }
    
    protected void reposition(Orientation reposX, Orientation reposY) {
        Orientation orientation = rectangle.getOrientation();
        int renderX = (int) rectangle.getX();
        int renderY = (int) rectangle.getY();
        
        if(orientation == reposX)
            renderX += size - DEPTH;
        
        else if(orientation == reposY)
            renderY += size - DEPTH;
            
        rectangle.setLocation(renderX, renderY);
    }
}
