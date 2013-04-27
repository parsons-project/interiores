package interiores.presentation.swing.views.map;

import interiores.business.models.Orientation;
import interiores.core.Debug;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author hector0193
 */
public class Door
    extends FixedElement
{
    protected static final int DEPTH = Walls.getDepth();
    private static final Color COLOR = Color.decode("#EEEEEE");
    
    private boolean hasToOpenOutwards;

    
    public Door(int size) {
        this(0, 0, size);
    }
        
    public Door(int x, int y, int size) {
        super(x, y, size, DEPTH);
        
        hasToOpenOutwards = false;
    }
    
    public void openOutwards() {
        hasToOpenOutwards = true;
    }
    
    public boolean hasToOpenOutwards() {
        return hasToOpenOutwards;
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(COLOR);
        g.fill(rectangle);
        
        g.setColor(Color.black);
        g.draw(rectangle);
        
        Debug.println("Drawing door at (" + rectangle.getX() + ", " + rectangle.getY() + ") and size " + size);
    }
    
    @Override
    public void setPosition(int x, int y, Orientation orientation) {
        super.setPosition(x, y, orientation);
        
        int renderX = x;
        int renderY = y;
        
        switch(orientation) {
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
        
        rectangle.setLocation(renderX + GridMap.getPadding(), renderY + GridMap.getPadding());
        rectangle.setOrientation(orientation);
    }
}
