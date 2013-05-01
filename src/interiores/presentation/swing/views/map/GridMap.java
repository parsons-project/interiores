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
public class GridMap
    implements Drawable
{
    private static final String GRID_COLOR = "#EEEEEE";
    private static final int RESOLUTION = 5;
    private static final int PADDING = 10;
    private static final double SCALE = 2;
    
    private int width;
    private int depth;
    private Map<Point, Drawable> elements;
    private Walls walls;
    private boolean isGridEnabled;
    
    public GridMap(int roomWidth, int roomDepth) {
        width = roomWidth + getPadding() * 2;
        depth = roomDepth + getPadding() * 2;
        elements = new HashMap();
        walls = new Walls(roomWidth, roomDepth);
        isGridEnabled = false;
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
        
        if(isGridEnabled)
            drawGrid(g);
        
        walls.draw(g);
        
        for(Drawable element : elements.values())
            element.draw(g);
    }
    
    private void drawGrid(Graphics2D g) {
        int rows = width / RESOLUTION;
        int cols = depth / RESOLUTION;
        
        g.setColor(Color.decode(GRID_COLOR));
        
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++)
                g.drawRect(i*5, j*5, 5, 5);
        }
    }
    
    public static int getPadding() {
        return RESOLUTION * PADDING;
    }
    
    public void enableGrid() {
        isGridEnabled = true;
    }
    
    public int getWidth() {
        return (int)(width * SCALE);
    }
    
    public int getHeight() {
        return (int)(depth * SCALE);
    }
}
