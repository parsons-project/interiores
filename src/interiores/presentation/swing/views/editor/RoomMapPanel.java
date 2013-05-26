package interiores.presentation.swing.views.editor;

import interiores.presentation.swing.views.editor.tools.EditorTool;
import interiores.business.controllers.DesignController;
import interiores.business.controllers.FixedElementController;
import interiores.business.controllers.FurnitureTypeController;
import interiores.business.controllers.RoomController;
import interiores.business.events.backtracking.SolveDesignFinishedEvent;
import interiores.business.events.backtracking.SolveDesignStartedEvent;
import interiores.business.events.furniture.ElementUnselectedEvent;
import interiores.business.events.room.RoomDesignChangedEvent;
import interiores.business.events.room.WantedFixedChangedEvent;
import interiores.business.models.OrientedRectangle;
import interiores.core.Debug;
import interiores.core.presentation.SwingController;
import interiores.core.presentation.annotation.Listen;
import interiores.presentation.swing.views.editor.tools.ToolManageable;
import interiores.presentation.swing.views.map.InteractiveRoomMap;
import interiores.presentation.swing.views.map.RoomMap;
import interiores.presentation.swing.views.map.RoomMapDebugger;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

/**
 *
 * @author hector
 */
public class RoomMapPanel
    extends JPanel
    implements ToolManageable
{
    private RoomController roomController;
    private FurnitureTypeController ftController;
    private FixedElementController fixedController;
    private DesignController designController;
    private SwingController swing;
    
    private InteractiveRoomMap map;
    private RoomMapDebuggerFrame debuggerGui;
    
    private EditorTool activeTool;
    private Point previous;

    /**
     * Creates new form RoomMap
     */
    public RoomMapPanel(SwingController presentation) throws Exception
    {
        initComponents();
        
        swing = presentation;
        
        roomController = presentation.getBusinessController(RoomController.class);
        ftController = presentation.getBusinessController(FurnitureTypeController.class);
        designController = presentation.getBusinessController(DesignController.class);
        fixedController = presentation.getBusinessController(FixedElementController.class);
        
        
        if(Debug.isEnabled())
            debuggerGui = presentation.get(RoomMapDebuggerFrame.class);
        
        previous = new Point(0, 0);
        
        initMap();
    }
        
    private void initMap()
    {
        int width = roomController.getWidth();
        int depth = roomController.getDepth();
        
        if(Debug.isEnabled()) {
            map = new RoomMapDebugger(width, depth); // Debug mode! Let's load a debuggable map!
            debuggerGui.setDebuggee(this);
        }
        else
            map = new InteractiveRoomMap(width, depth); // A simple room map on production
        
        setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
        
        updateFixed();
        updateDesign();
    }
    
    @Override
    public void setActiveTool(EditorTool activeTool) {
        this.activeTool = activeTool;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this
     * code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });
        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_formKeyReleased
    {//GEN-HEADEREND:event_formKeyReleased
        boolean shouldRepaint = false;
        if(evt.getKeyCode() == KeyEvent.VK_DELETE) {
            for(String id : map.getSelected())
                ftController.unselect(id);
            
            shouldRepaint = true;
        }
        
        if(shouldRepaint || activeTool.keyReleased(evt, map))
            repaint();
    }//GEN-LAST:event_formKeyReleased

    private void formMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMouseDragged
    {//GEN-HEADEREND:event_formMouseDragged
        requestFocus();
        
        if(activeTool.mouseDragged(evt, map))
            repaint();
    }//GEN-LAST:event_formMouseDragged

    private void formMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMouseMoved
    {//GEN-HEADEREND:event_formMouseMoved
        if(activeTool.mouseMoved(evt, map))
            repaint();
    }//GEN-LAST:event_formMouseMoved

    private void formMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMousePressed
    {//GEN-HEADEREND:event_formMousePressed
        requestFocus();
        
        if(activeTool.mousePressed(evt, map))        
            repaint();
    }//GEN-LAST:event_formMousePressed

private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
    if (activeTool.mouseReleased(evt, map))
        repaint();
}//GEN-LAST:event_formMouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void paintComponent(Graphics g)
    {
        if(map != null) {
            Graphics2D g2d = (Graphics2D) g;
            map.setSize(getSize());
            map.draw(g2d);
        }
    }
    
    public RoomMap getRoomMap() {
        return map;
    }
      
    @Listen(SolveDesignStartedEvent.class)
    public void putSearching() {
        map.setStatus("Searching...");
        
        repaint();
    }
    
    @Listen(RoomDesignChangedEvent.class)
    public void updateDesign() {
        map.clearFurniture();
        map.addFurniture(designController.getDesignFurniture());

        repaint();
    }
    
    @Listen(WantedFixedChangedEvent.class)
    public void updateFixed() {
        map.clearFixed();
        map.addFixed(fixedController.getWantedFixed());
        
        repaint();
    }
    
    @Listen(SolveDesignFinishedEvent.class)
    public void updateStatus(SolveDesignFinishedEvent event) {
        if (event.isSolutionFound())
            map.setStatus("Solution found! :)");
        else
            map.setStatus("Solution not found :(");
        
        if (event.hasTime())
            map.setTime(event.getTime());
        
        repaint();
    }
    
    public void addFurniture(String name, OrientedRectangle area, Color color) {
        map.addFurniture(name, area, color);
    }
    
    @Listen(ElementUnselectedEvent.class)
    public void removeFurniture(ElementUnselectedEvent evt) {
        map.removeFurniture(evt.getName());
        repaint();
    }
}
