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
    private static final int DEPTH = Walls.getDepth();
    private static final Color COLOR = Color.decode("#EEEEEE");
    
    private OrientedRectangle r;
    private int size;
    
    public Door(int x, int y, int size, Orientation o) {
        r = new OrientedRectangle(x + GridMap.getPadding(), y + GridMap.getPadding(), DEPTH, size, o);
        this.size = size;
    }
    
    public int getSize() {
        return size - DEPTH;
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(COLOR);
        g.fill(r);
        
        g.setColor(Color.black);
        g.draw(r);

        Debug.println("Drawing door at (" + r.getX() + ", " + r.getY() + ") and size " + r.getHeight());
    }
}
