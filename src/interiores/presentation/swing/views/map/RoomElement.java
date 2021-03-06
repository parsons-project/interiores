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
    
    protected String name;
    protected OrientedRectangle rectangle;
    protected Color color;
    private boolean isSelected;
    
    public RoomElement(String name, OrientedRectangle area) {
        this(name, area, DEFAULT_COLOR);
    }
    
    public RoomElement(String name, OrientedRectangle area, Color color) {
        this.name = name;
        rectangle = (OrientedRectangle) area.clone();
        
        rectangle.translate(RoomMap.getPadding(), RoomMap.getPadding());
        this.color = color;
        isSelected = false;
    }
    
    public String getName() {
        return name;
    }
    
    public Point getPosition() {
        return rectangle.getLocation();
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
    
    public void translate(int dx, int dy) {
        rectangle.translate(dx, dy);
    }
}
