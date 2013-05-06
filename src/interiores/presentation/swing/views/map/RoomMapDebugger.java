package interiores.presentation.swing.views.map;

import interiores.core.Debug;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hector
 */
public class RoomMapDebugger
    extends RoomMap
{
    private static final Color COLOR_GRID = Color.decode("#EEEEEE");
    private static final Color COLOR_POINT = Color.decode("#C3D9FF"); // Gmail blue
    
    private boolean isGridEnabled;
    private List<Point> points;
        
    public RoomMapDebugger(int roomWidth, int roomDepth) {
        super(roomWidth, roomDepth);
        
        isGridEnabled = Debug.isEnabled();
        points = new ArrayList();
    }
    
    public void enableGrid() {
        isGridEnabled = true;
    }
    
    public void disableGrid() {
        isGridEnabled = false;
    }
    
    public boolean isGridEnabled() {
        return isGridEnabled;
    }
    
    public void addPoint(Point p) {
        points.add(p);
    }
    
    @Override
    protected void drawElements(Graphics2D g)
    {
        g.setColor(COLOR_POINT);
        
        for(Point p : points)
            g.fillRect(p.x + getPadding(), p.y + getPadding(), RESOLUTION, RESOLUTION);
        
        if(isGridEnabled)
            drawGrid(g);
        
        super.drawElements(g);
    }
    
    private void drawGrid(Graphics2D g) {
        int rows = width / RESOLUTION;
        int cols = depth / RESOLUTION;
        
        g.setColor(COLOR_GRID);
        
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++)
                g.drawRect(i*5, j*5, 5, 5);
        }
    }
   
    @Override
    public void clear()
    {
        super.clear();
        points.clear();
    }
}
