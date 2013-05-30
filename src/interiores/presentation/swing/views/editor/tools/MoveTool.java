package interiores.presentation.swing.views.editor.tools;

import interiores.business.controllers.DesignController;
import interiores.business.models.room.elements.WantedFurniture;
import interiores.core.presentation.SwingController;
import interiores.presentation.swing.views.map.InteractiveRoomMap;
import interiores.presentation.swing.views.map.RoomElement;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author hector
 */
public class MoveTool
    extends EditorTool
{
    private DesignController design;
    private Point previous;
    
    public MoveTool(SwingController swing) {
        super("Move", "move.png", KeyEvent.VK_M);
        
        this.design = swing.getBusinessController(DesignController.class);
    }
    
    @Override
    public boolean mouseReleased(MouseEvent evt, InteractiveRoomMap map) {
        for(RoomElement element : map.getSelected()) {
            Point p = element.getPosition();
            
            design.setPosition(element.getName(), map.unpad(p));
        }
        
        return true;
    }
    
    @Override
    public boolean mouseDragged(MouseEvent evt, InteractiveRoomMap map) {
        Point current = evt.getPoint();
        
        if(map.translateSelected(previous, current))
            previous = current; // Only if there has been some translation
        
        return true;
    }
    
    @Override
    public boolean mouseMoved(MouseEvent evt, InteractiveRoomMap map) {
        previous = evt.getPoint(); // This is only called when no mouse buttons are pressed :D
        
        return false; // Don't repaint
    }
}
