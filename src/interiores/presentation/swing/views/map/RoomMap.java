package interiores.presentation.swing.views.map;

import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.core.Debug;
import interiores.presentation.swing.views.map.doors.LeftDoor;
import interiores.presentation.swing.views.map.doors.RightDoor;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author hector
 */
public class RoomMap
    implements Drawable
{

    protected static final int RESOLUTION = 5;
    protected static final int PADDING = 10;
    protected static final double SCALE = 1.4;
    
    protected int width;
    protected int depth;
    private Map<Point, Drawable> elements;
    private Walls walls;
    private String status;
    private Drawable debugger;
    
    public RoomMap(int roomWidth, int roomDepth) {
        width = roomWidth + getPadding() * 2;
        depth = roomDepth + getPadding() * 2;
        elements = new HashMap();
        walls = new Walls(roomWidth, roomDepth);
        status = "";
    }
    
    public void clear() {
        elements.clear();
        status = "";
    }
    
    public void addDoor(String wall, int size, int displacement, boolean hasToOpenToLeft,
            boolean hasToOpenOutwards) {
        Door door;
        
        if(hasToOpenToLeft) door = new LeftDoor(size);
        else door = new RightDoor(size);
        
        walls.addDoor(door, Orientation.valueOf(wall), displacement);
    }
    
    public void addWindow(String wall, int size, int displacement) {
        Window window = new Window(size);
        
        walls.addWindow(window, Orientation.valueOf(wall), displacement);
    }
    
    public void addPillar(int x, int y, int width, int depth) {
        //elements.put(new Point(x, y), new RoomElement(x, y, width, depth));
    }
    
    public void addFurniture(String name, OrientedRectangle area, Color color) {
        Furniture furniture = new Furniture(name, area, color);
        
        elements.put(new Point((int)area.getX(), (int)area.getY()), furniture);
    }
    
    @Override
    public void draw(Graphics2D g) {
        Debug.println("Redrawing map...");
        
        g.scale(SCALE, SCALE);
        
        g.setColor(Color.white);
        g.fillRect(0, 0, width, depth);
        
        drawElements(g);
    }
    
    protected void drawElements(Graphics2D g) {
        walls.draw(g);
        
        for(Drawable element : elements.values())
            element.draw(g);
        
        g.setColor(Color.black);
        g.drawString(status, 10, 20);
    }
    
    public static int getPadding() {
        return RESOLUTION * PADDING;
    }
    
    public static int getResolution() {
        return RESOLUTION;
    }
    
    public int getWidth() {
        return (int)(width * SCALE);
    }
    
    public int getHeight() {
        return (int)(depth * SCALE);
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setDebugger(Drawable debugger) {
        this.debugger = debugger;
    }
}
