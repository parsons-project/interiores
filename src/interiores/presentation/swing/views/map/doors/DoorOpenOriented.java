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
        Orientation orientation = r.getOrientation();
        int renderX = (int) r.getX();
        int renderY = (int) r.getY();
        
        if(orientation == reposX)
            renderX += size - DEPTH;
        
        else if(orientation == reposY)
            renderY += size - DEPTH;
            
        r.setLocation(renderX, renderY);
    }
}
