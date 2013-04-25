package interiores.presentation.swing.views.map;

import interiores.business.models.Orientation;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hector
 */
public class Walls implements Drawable {
    private static final int DEPTH = 5;
    private static final String COLOR = "0x999999";
    private int width;
    private int height;
    private Map<Point, Door> doors;
    
    public Walls(int width, int height) {
        this.width = width;
        this.height = height;
        doors = new HashMap();
    }
    
    public static int getDepth() {
        return DEPTH;
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.decode(COLOR));
        
        drawHorizontalWall(g, 0, DEPTH);
        drawHorizontalWall(g, height, 0);
        
        drawVerticalWall(g, 0, DEPTH);
        drawVerticalWall(g, width, 0);
        
        for(Door door : doors.values())
            door.draw(g);
    }
    
    public void drawHorizontalWall(Graphics2D g, int y, int valign) {
        int padding = GridMap.getPadding();
        int drawableWidth = width + DEPTH;
        
        for(int i = 0 - DEPTH; i < drawableWidth; i += DEPTH) {
            Point p = new Point(i, y - valign);
        
            if(doors.containsKey(p))
                i += doors.get(p).getSize();
            else
                g.fillRect(i + padding, y + padding - valign, DEPTH, DEPTH);
        }
    }
    
    public void drawVerticalWall(Graphics2D g, int x, int halign) {
        int padding = GridMap.getPadding();
        
        for(int i = 0; i < width; i += DEPTH) {
            Point p = new Point(x - halign, i);
        
            if(doors.containsKey(p))
                i += doors.get(p).getSize();
            else
                g.fillRect(x + padding - halign, i + padding, DEPTH, DEPTH);
        }
    }
    
    public void addDoor(Orientation where, int pos, int size, String opensTo) {
        int x, y;
        Orientation o;
        
        x = y = -DEPTH;
        
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
        
        Point key = new Point(x, y);
        Door door = new Door(x, y, size, opensTo.equals("out") ? where : where.complementary());
        
        doors.put(key, door);
    }
}
