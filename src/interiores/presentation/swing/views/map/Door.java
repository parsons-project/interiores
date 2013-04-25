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
    
    private int x;
    private int y;
    private OrientedRectangle r;
    private int size;
    
    public Door(int x, int y, int size, Orientation o) {
        this.x = x;
        this.y = y;
        this.size = size;
        
        setOrientation(o);
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
    
    public final void setOrientation(Orientation o) {
        int renderX = x;
        int renderY = y;
        
        switch(o) {
            case E:
                renderX += Walls.getDepth();
                break;
                
            case N:
                renderY -= size;
                break;
                
            case W:
                renderX -= size;
                break;
                
            default:
                renderY += Walls.getDepth();
                break;
        }
        
        r = new OrientedRectangle(renderX + GridMap.getPadding(), renderY + GridMap.getPadding(),
                DEPTH, size, Orientation.S);
        r.setOrientation(o);
    }
}
