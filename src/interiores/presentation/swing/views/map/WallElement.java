package interiores.presentation.swing.views.map;

import interiores.business.models.Orientation;
import interiores.business.models.room.FurnitureModel;
import java.awt.Point;

/**
 *
 * @author hector
 */
abstract public class WallElement
    extends RoomElement
{
    protected int x;
    protected int y;
    protected int size;
    
    public WallElement(String name, FurnitureModel model) {
        super(name, model.getActiveArea(new Point(0, 0), Orientation.S));
        
        x = 0;
        y = 0;
        size = model.getSize().width;
        color = model.getColor();
    }
    
    public int getSize() {
        return size;
    }
    
    public void setPosition(int x, int y, Orientation orientation) {
        this.x = x;
        this.y = y;
        
        rectangle.setLocation(x + RoomMap.getPadding(), y + RoomMap.getPadding());
        rectangle.setOrientation(orientation);
    }
}
