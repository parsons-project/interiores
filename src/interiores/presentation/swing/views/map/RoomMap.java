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
    private Map<String, RoomElement> elements;
    private Walls walls;
    private String status;
    private String time;
    
    public RoomMap(int roomWidth, int roomDepth) {
        width = roomWidth + getPadding() * 2;
        depth = roomDepth + getPadding() * 2;
        elements = new HashMap();
        walls = new Walls(roomWidth, roomDepth);
        status = "";
        time = "";
    }
    
    public void clearFurniture() {
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
        // @TODO Pillars
        //elements.put(new Point(x, y), new RoomElement(x, y, width, depth));
    }
    
    public void addFurniture(String name, OrientedRectangle area, Color color) {
        Furniture furniture = new Furniture(name, area, color);
        
        elements.put(name, furniture);
    }
    
    public void removeFurniture(String name) {
        elements.remove(name);
    }
    
    public RoomElement getElementAt(int x, int y) {
        for(RoomElement element : elements.values()) {
            if(element.contains(new Point(x, y)))
                return element;
        }
        
        return null;
    }
    
    @Override
    public void draw(Graphics2D g) {
        Debug.println("Redrawing map...");
        
        g.scale(SCALE, SCALE);
        
        g.setColor(Color.white);
        g.fillRect(0, 0, width, depth);
        
        walls.draw(g);
        
        drawElements(g);
        
        g.setColor(Color.black);
        g.drawString(status, 10, 20);
        g.drawString(time, 10, depth - 20);
    }
    
    protected void drawElements(Graphics2D g)
    {
        for(Drawable element : elements.values())
            element.draw(g);
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
    
    public void setTime(long time) {
        // This might be ugly
        String[] scale = {"ns", "us", "ms", "s"};
        int iters = 0;
        float d_time = time;
        while (d_time > 100 && iters < 3) {
            d_time /= 1000.0;
            ++iters;
        }
        String timeString;
        if (d_time > 100) {
            //we are in the minutes range
            int min = (int)d_time / 60;
            d_time %= 60;
            timeString = String.valueOf(min) + "m" + String.valueOf(d_time) + "s";
        }
        else {
            timeString = String.valueOf(d_time) + scale[iters];
        }
         
        this.time = "Took: " + timeString;
    }
}
