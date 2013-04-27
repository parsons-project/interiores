package interiores.presentation.swing.views.map;

import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;

/**
 *
 * @author hector
 */
abstract public class FixedElement
    implements Drawable
{
    protected int x;
    protected int y;
    protected int size;
    protected OrientedRectangle rectangle;
    
    public FixedElement(int x, int y, int size, int depth) {
        this.x = x;
        this.y = y;
        this.size = size;
        
        rectangle = new OrientedRectangle(x + GridMap.getPadding(), y + GridMap.getPadding(), depth, size,
                Orientation.S);
    }
    
    public int getSize() {
        return size;
    }
    
    public void setPosition(int x, int y, Orientation orientation) {
        this.x = x;
        this.y = y;
        
        rectangle.setLocation(x + GridMap.getPadding(), y + GridMap.getPadding());
        rectangle.setOrientation(orientation);
    }
}
