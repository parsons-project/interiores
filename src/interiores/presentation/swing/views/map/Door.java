package interiores.presentation.swing.views.map;

import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.core.Debug;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author hector0193
 */
public class Door
    implements Drawable
{
    private static final Color COLOR = Color.decode("#006699");
    private OrientedRectangle r;
    
    public Door(int x, int y, int size, Orientation o) {
        r = new OrientedRectangle(x + GridMap.getPadding(), y + GridMap.getPadding(), 3, size, o);
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(COLOR);
        g.fill(r);
        g.draw(r);
        
        Debug.println("Drawing door at (" + r.getX() + ", " + r.getY() + ") and size " + r.getHeight());
    }
}
