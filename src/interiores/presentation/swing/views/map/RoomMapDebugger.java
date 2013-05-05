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
    private static final String GRID_COLOR = "#EEEEEE";
    
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
    public void drawElements(Graphics2D g)
    {      
        if(isGridEnabled)
            drawGrid(g);
        
        super.drawElements(g);
    }
    
    private void drawGrid(Graphics2D g) {
        int rows = width / RoomMap.getResolution();
        int cols = depth / RoomMap.getResolution();
        
        g.setColor(Color.decode(GRID_COLOR));
        
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++)
                g.drawRect(i*5, j*5, 5, 5);
        }
    }
}
