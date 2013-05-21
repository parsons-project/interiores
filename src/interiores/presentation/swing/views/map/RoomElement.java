package interiores.presentation.swing.views.map;

import interiores.business.models.OrientedRectangle;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author hector
 */
public class RoomElement
    implements Drawable
{
    private static final Color DEFAULT_COLOR = Walls.getColor();
    
    protected OrientedRectangle rectangle;
    protected Color color;
    private boolean isSelected;
    
    public RoomElement(OrientedRectangle area) {
        this(area, DEFAULT_COLOR);
    }
    
    public RoomElement(OrientedRectangle area, Color color) {
        rectangle = (OrientedRectangle) area.clone();
        
        rectangle.translate(RoomMap.getPadding(), RoomMap.getPadding());
        this.color = color;
        isSelected = false;
    }
    
    public void setColor(String c)
    {
        color = Color.decode(c);
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        g.setColor(color);
        g.fill(rectangle);
    }
    
    public boolean contains(Point p) {
        return rectangle.contains(p);
    }
    
    public boolean isSelected() {
        return isSelected;
    }
    
    public void select() {
        isSelected = true;
    }
    
    public void unselect() {
        isSelected = false;
    }
}
