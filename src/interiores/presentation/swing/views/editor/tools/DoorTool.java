package interiores.presentation.swing.views.editor.tools;

import interiores.business.controllers.FixedElementController;
import interiores.core.presentation.SwingController;
import interiores.presentation.swing.views.map.InteractiveRoomMap;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author alvaro
 */
public class DoorTool
    extends OnWallElementTool 
{
    private static int MAX_LENGTH = 100;
    
    public DoorTool(SwingController swing) {
        super("Door", "door.png", KeyEvent.VK_D);   
        fixedController = swing.getBusinessController(FixedElementController.class);
    }
    
    @Override
    public boolean mouseReleased(MouseEvent evt, InteractiveRoomMap map) {
        if(position == null)
            return false;
        
        int length = getLength(map.normDiscretize(evt.getPoint()));
        
        if(length == 0)
            return true;
        
        if (length > MAX_LENGTH) length = MAX_LENGTH; else if (length < -MAX_LENGTH) length = -MAX_LENGTH;
        int displacement = getDisplacement(length);
        
        fixedController.addDoor(wallWhere.toString(false), pad(displacement), pad(Math.abs(length)));
        
        return true;
    }
    
    @Override
    public boolean mouseDragged(MouseEvent evt, InteractiveRoomMap map) {
        if(position == null)
            return false;
        
        int length = getLength(map.normDiscretize(evt.getPoint()));
        
        if(length == 0)
            return true;
        
        if (length > MAX_LENGTH) length = MAX_LENGTH; else if (length < -MAX_LENGTH) length = -MAX_LENGTH;
        int displacement = getDisplacement(length);
        
        map.previewDoor(wallWhere, pad(displacement), pad(Math.abs(length)));
        return true;
    }
    
}
