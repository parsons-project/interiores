package interiores.presentation.swing.views.map;

import interiores.business.models.Orientation;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hector
 */
public class Walls implements Drawable {
    private static final int DEPTH = 5;
    private static final String COLOR = "0x999999";
    private int width;
    private int height;
    private List<Door> doors;
    
    public Walls(int width, int height) {
        this.width = width;
        this.height = height;
        doors = new ArrayList();
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.decode(COLOR));
        
        int padding = GridMap.getPadding();
        
        g.fillRect(padding - DEPTH, padding - DEPTH, width + DEPTH * 2, DEPTH);
        g.fillRect(padding - DEPTH, padding + height, width + DEPTH * 2, DEPTH);
        g.fillRect(padding - DEPTH, padding, DEPTH, height);
        g.fillRect(padding + width, padding, DEPTH, height);
        
        for(Door door : doors)
            door.draw(g);
    }
    
    public void addDoor(Orientation where, int pos, int size, Orientation o) {
        int x = 0, y = 0;
        
        switch(where) {
            case E:
                x = width;
                
            case W:
                y = pos;
                break;
            
            case S:
                y = height;
                
            case N:
                x = pos;
                break;
        }
        
        doors.add(new Door(x, y, size, o));
    }
}
