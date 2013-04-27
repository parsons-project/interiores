package interiores.presentation.swing.views.map;

import interiores.business.models.Orientation;
import interiores.core.Debug;
import interiores.presentation.swing.views.map.doors.LeftDoor;
import interiores.presentation.swing.views.map.doors.RightDoor;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

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
    private List<Drawable> elements;
    private boolean isGridEnabled;
    
    public GridMap(int roomWidth, int roomDepth) {
        width = roomWidth + getPadding() * 2;
        depth = roomDepth + getPadding() * 2;
        
        elements = new ArrayList();

        Walls walls = new Walls(roomWidth, roomDepth);
        
        Door door1 = new RightDoor(30);
        door1.openOutwards();
        
        Door door2 = new LeftDoor(30);
        door2.openOutwards();
        
        Door door3 = new RightDoor(30);
        
        walls.addDoor(door1, Orientation.N, 50);
        walls.addDoor(door2, Orientation.S, 50);
        walls.addDoor(door3, Orientation.E, 80);
        
        Window window1 = new Window(50);
        Window window2 = new Window(80);
        
        walls.addWindow(window1, Orientation.W, 150);
        walls.addWindow(window2, Orientation.N, 170);
        
        Furniture sofa = new Furniture("Sofa", 0, 0, 100, 40);
        sofa.setOrientation(Orientation.E);
        sofa.setColor("#BDB76B");
        
        Furniture tv = new Furniture("TV", 100, 15, 70, 20);
        tv.setOrientation(Orientation.W);
        tv.setColor("#EEEEEE");
        
        elements.add(walls);
        elements.add(sofa);
        elements.add(tv);
        elements.add(new RoomElement(270, 270, 30, 30));
       
        isGridEnabled = false;
    }
    
    @Override
    public void draw(Graphics2D g) {
        Debug.println("Redrawing map...");
        
        g.scale(SCALE, SCALE);
        
        g.setColor(Color.white);
        g.fillRect(0, 0, width, depth);
        
        if(isGridEnabled)
            drawGrid(g);
        
        for(Drawable element : elements)
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
