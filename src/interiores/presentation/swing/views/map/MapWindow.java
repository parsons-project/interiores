package interiores.presentation.swing.views.map;

import interiores.business.models.Orientation;
import interiores.business.models.room.FurnitureModel;
import interiores.utils.Dimension;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author hector
 */
public class MapWindow extends WallElement
{
    private static final Color COLOR = Color.decode("#006699");
    private static final int DEPTH = Walls.getDepth();
    
    public MapWindow(String name, int length, Color color, Point position, Orientation orientation) {
        super(name, new Dimension(length, DEPTH), color);
        
        setPosition(position.x, position.y, orientation);
    }
    
    public MapWindow(String name, FurnitureModel model, Point position, Orientation orientation) {
        super(name, model);
        
        setPosition(position.x, position.y, orientation);
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.fill(rectangle);
        
        g.setColor(COLOR);
        g.draw(rectangle);
    }
}
