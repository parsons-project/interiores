package interiores.presentation.swing.views.map;

import interiores.core.Debug;
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
    private static final int GRID_RES = 5;
    private static final int GRID_PAD = 10;
    private static final String GRID_COLOR = "0xEEEEEE";
    private static final double SCALE = 2;
    
    private int width;
    private int height;
    private List<Drawable> elements;
    private boolean isGridEnabled;
    
    public GridMap(int roomWidth, int roomHeight) {
        width = roomWidth + getPadding() * 2;
        height = roomHeight + getPadding() * 2;
        
        elements = new ArrayList();
        // Add initial walls
        elements.add(new Walls(roomWidth, roomHeight));
        
        Furniture sofa = new Furniture("Sofa", 0, 0, 100, 40);
        sofa.setOrientation('R');
        sofa.setColor("#BDB76B");
        
        Furniture tv = new Furniture("TV", 100, 15, 70, 20);
        tv.setOrientation('L');
        tv.setColor("#EEEEEE");
        
        elements.add(sofa);
        elements.add(tv);
       
        isGridEnabled = false;
    }
    
    @Override
    public void draw(Graphics2D g) {
        Debug.println("Redrawing map...");
        
        g.scale(SCALE, SCALE);
        
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        
        if(isGridEnabled)
            drawGrid(g);
        
        for(Drawable element : elements)
            element.draw(g);
    }
    
    private void drawGrid(Graphics2D g) {
        int rows = width / GRID_RES;
        int cols = height / GRID_RES;
        
        g.setColor(Color.decode(GRID_COLOR));
        
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
                g.drawRect(i*5, j*5, 5, 5);
        }
    }
    
    public static int getPadding() {
        return GRID_RES * GRID_PAD;
    }
    
    public void enableGrid() {
        isGridEnabled = true;
    }
    
    public int getWidth() {
        return (int)(width * SCALE);
    }
    
    public int getHeight() {
        return (int)(height * SCALE);
    }
}
