package interiores.presentation.swing.views.map;

import interiores.business.models.Orientation;
import interiores.business.models.room.FurnitureModel;
import interiores.core.Debug;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author hector0193
 */
public class MapDoor
    extends WallElement
{
    private boolean hasToOpenOutwards;
    protected Orientation orientation;
        
    public MapDoor(String name, FurnitureModel model) {
        super(name, model);
        
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
        g.setColor(color);
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
        
        this.orientation = orientation;
        
        Debug.println(x + " " + y);
        
        switch(orientation) {
            case E:
                renderX -= size;
                break;
                
            case N:
                renderY += Walls.getDepth();
                break;
                
            case W:
                renderX += Walls.getDepth();
                break;
                
            default:
                renderY -= size;
                break;
        }
        
        rectangle.setLocation(renderX + RoomMap.getPadding(), renderY + RoomMap.getPadding());
        rectangle.setOrientation(orientation);
        rectangle.rotateLeft();
    }
}
