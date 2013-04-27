package interiores.presentation.swing.views.map;

import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author hector
 */
public class RoomElement
    implements Drawable
{
    private static final Color DEFAULT_COLOR = Walls.getColor();
    
    protected OrientedRectangle rectangle;
    private Color color;
    
    public RoomElement(int x, int y, int width, int depth) {
        this(x, y, width, depth, DEFAULT_COLOR);
    }
    
    public RoomElement(int x, int y, int width, int depth, Color color) {
        rectangle = new OrientedRectangle(x  + GridMap.getPadding(), y + GridMap.getPadding(), width, depth,
                Orientation.S);
        
        this.color = color;
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
}
