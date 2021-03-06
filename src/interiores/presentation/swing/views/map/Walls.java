package interiores.presentation.swing.views.map;

import interiores.business.models.Orientation;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.room.elements.WantedFixed;
import interiores.presentation.swing.views.map.doors.RightDoor;
import interiores.utils.CoolColor;
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
    private static final Color COLOR = Color.decode("#999999");
    private int width;
    private int depth;
    private Map<Point, WallElement> elements;
    private Point previewKey;
    
    public Walls(int width, int height) {
        this.width = width;
        this.depth = height;
        elements = new HashMap();
    }
    
    public static int getDepth() {
        return DEPTH;
    }
    
    public static Color getColor() {
        return COLOR;
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(COLOR);
        
        drawHorizontalWall(g, 0, DEPTH);
        drawHorizontalWall(g, depth, 0);
        
        drawVerticalWall(g, 0, DEPTH);
        drawVerticalWall(g, width, 0);
        
        for(WallElement element : elements.values())
            element.draw(g);
        
        if(previewKey != null) {
            elements.remove(previewKey);
            previewKey = null;
        }
    }
    
    public void drawHorizontalWall(Graphics2D g, int y, int valign) {
        int padding = RoomMap.getPadding();
        int drawableWidth = width + DEPTH;
        
        for(int i = 0 - DEPTH; i < drawableWidth; i += DEPTH) {
            Point p = new Point(i, y - valign);
        
            if(elements.containsKey(p))
                i += elements.get(p).getSize() - DEPTH;
            else
                g.fillRect(i + padding, y + padding - valign, DEPTH, DEPTH);
        }
    }
    
    public void drawVerticalWall(Graphics2D g, int x, int halign) {
        int padding = RoomMap.getPadding();
        
        for(int i = 0; i < depth; i += DEPTH) {
            Point p = new Point(x - halign, i);
        
            if(elements.containsKey(p))
                i += elements.get(p).getSize() - DEPTH;
            else
                g.fillRect(x + padding - halign, i + padding, DEPTH, DEPTH);
        }
    }
    
    private Point getPosition(Orientation wall, int displacement) {
        Point p = new Point(-DEPTH, -DEPTH);
        
        switch(wall) {
            case E:
                p.x = width;
                
            case W:
                p.y = displacement;
                break;
            
            case S:
                p.y = depth;
                
            case N:
                p.x = displacement;
                break;
        }
        
        return p;
    }
    
    public int getDistanceToWall(Orientation orientation, Point p) {
        switch (orientation) {
            case N:
                return Math.abs(RoomMap.getPadding() - p.y);
            case S:
                return Math.abs(RoomMap.getPadding() + depth - p.y);
            case W:
                return Math.abs(RoomMap.getPadding() - p.x);
            case E:
                return Math.abs(RoomMap.getPadding() + width - p.x);
            default:
                return -1;
        }
    }
    
    public void addDoor(WantedFixed door) {        
        FurnitureValue value = door.getAssignedValue();
        Point key = value.getPosition();
        
        // Only right doors for now :/
        RightDoor mapDoor = new RightDoor(door.getName(), value.getModel(), key,
                value.getOrientation());
        
        elements.put(key, mapDoor);
    }
       
    public void previewDoor(Orientation wall, int displacement, int length) {
        Point position = getPosition(wall, displacement);
        
        RightDoor previewDoor = new RightDoor("preview", length, CoolColor.BROWN.getColor(), position, wall);
        
        if(! elements.containsKey(position)) {
            elements.put(position, previewDoor);
            previewKey = position;
        }
    }
    
    public void addWindow(WantedFixed window) {
        FurnitureValue value = window.getAssignedValue();
        Point key = value.getPosition();
        
        MapWindow mapWindow = new MapWindow(window.getName(), value.getModel(), value.getPosition(),
                value.getOrientation());
        
        elements.put(key, mapWindow);
    }
    
    public void previewWindow(Orientation wall, int displacement, int length) {
        Point position = getPosition(wall, displacement);
        
        MapWindow previewWindow = new MapWindow("previewWin", length, CoolColor.WHITE.getColor(), position, wall);
        
        if(! elements.containsKey(position)) {
            elements.put(position, previewWindow);
            previewKey = position;
        }
    }
    
    public void clear() {
        elements.clear();
    }
}
