package interiores.presentation.swing.views;

import interiores.business.models.OrientedRectangle;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.core.Debug;
import interiores.core.presentation.annotation.Event;
import interiores.presentation.swing.SwingPanel;
import interiores.presentation.swing.views.map.GridMap;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author hector
 */
public class RoomMapPanel extends SwingPanel
{
    private GridMap map;
    private String info;

    /**
     * Creates new form RoomMap
     */
    public RoomMapPanel()
    {
        map = null;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this
     * code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void paintComponent(Graphics g)
    {
        if(map != null)
            map.draw((Graphics2D) g);
        g.drawString(info, 10, 10);
    }
    
    @Event(paramNames = {"width", "depth"})
    public void roomCreated(int width, int depth)
    {
        map = new GridMap(width, depth);
        
        if(Debug.isEnabled())
            map.enableGrid();
        
        setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));  
    }
    
    @Event(paramNames = {"width", "depth"})
    public void roomLoaded(int width, int depth)
    {
        roomCreated(width, depth);
    }
    
    @Event(paramNames = {"design"})
    public void roomDesigned(Map<String, FurnitureValue> design) {
        for(Entry<String, FurnitureValue> entry : design.entrySet()) {
            String name = entry.getKey();
            Color color = entry.getValue().getModel().getColor();
            
            furnitureAdded(name, entry.getValue().getArea(), color);
        }
        
        repaint();
    }
    
    @Event(paramNames = {"name", "x", "y", "width", "depth", "orientation"})
    public void furnitureAdded(String name, OrientedRectangle area, Color color) {
        map.addFurniture(name, area, color);
    }
    
    @Event(paramNames = {"isFound"})
    public void designFinished(boolean isFound) {
        if (isFound) info = "Solution found :)";
        else info = "Solution not found :(";
        repaint();
    }
    
    @Event
    public void designStarted() {
        info = "Searching...";
        repaint();
    }
    
}
