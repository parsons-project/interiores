package interiores.presentation.swing.views.map;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author hector
 */
public class Window extends WallElement
{
    private static final int DEPTH = Walls.getDepth();
    private static final Color COLOR = Color.decode("#006699");
    
    public Window(int size) {
        this(0, 0, size);
    }
    
    public Window(int x, int y, int size) {
        super(x, y, size, DEPTH);
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.fill(rectangle);
        
        g.setColor(COLOR);
        g.draw(rectangle);
    }
}
