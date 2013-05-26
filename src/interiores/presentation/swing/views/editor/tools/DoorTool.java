package interiores.presentation.swing.views.editor.tools;

import interiores.business.controllers.FixedElementController;
import interiores.business.models.Orientation;
import interiores.core.presentation.SwingController;
import interiores.presentation.swing.views.map.InteractiveRoomMap;
import interiores.presentation.swing.views.map.RoomMap;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author alvaro
 */
public class DoorTool
    extends EditorTool 
{
    private SwingController swing;
    private FixedElementController fixedController;
    private Point position;
    private Orientation wallWhere;
    
    public DoorTool(SwingController swing) {
        super("Door", "door.png", KeyEvent.VK_D);
        
        this.swing = swing;
        fixedController = swing.getBusinessController(FixedElementController.class);
    }
    
    @Override
    public boolean mouseReleased(MouseEvent evt, InteractiveRoomMap map) {
        if(position == null)
            return false;
        
        int length = getLength(map.normDiscretize(evt.getPoint()));
        
        if(length == 0)
            return true;
        
        int displacement = getDisplacement();
        
        fixedController.addDoor(wallWhere.toString(false), displacement, length);
        
        return true;
    }
    
    @Override
    public boolean mouseDragged(MouseEvent evt, InteractiveRoomMap map) {
        if(position == null)
            return false;
        
        int length = getLength(map.normDiscretize(evt.getPoint()));
        
        if(length == 0)
            return true;
        
        int displacement = getDisplacement();
        
        map.previewDoor(wallWhere, displacement, length);
        return true;
    }
    
    @Override
    public boolean mousePressed(MouseEvent evt, InteractiveRoomMap map) {
        position  = map.normDiscretize(evt.getPoint());
        wallWhere = map.getNearestWall(evt.getX(), evt.getY());
   
        return false;
    }
    
    private int getLength(Point normPos) {
        if(wallWhere == Orientation.E || wallWhere == Orientation.W)
            return Math.abs(position.y - normPos.y);
        
        return Math.abs(position.x - normPos.x);
    }
    
    private int getDisplacement() {
        if(wallWhere == Orientation.E || wallWhere == Orientation.W)
            return position.y - RoomMap.getPadding();
            
        return position.x - RoomMap.getPadding();
    }
}
