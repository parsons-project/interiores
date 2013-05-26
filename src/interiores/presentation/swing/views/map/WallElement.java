package interiores.presentation.swing.views.map;

import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.business.models.room.FurnitureModel;
import interiores.utils.Dimension;
import java.awt.Color;
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
    
    public WallElement(String name, Dimension size, Color color) {
        super(name, new OrientedRectangle(new Point(0, 0), size, Orientation.S));
        
        x = 0;
        y = 0;
        
        this.size = size.width;
        this.color = color;
    }
    
    public WallElement(String name, FurnitureModel model) {
        this(name, model.getSize(), model.getColor());
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
