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
public class WindowTool 
    extends OnWallElementTool
{
    
    public WindowTool(SwingController swing) {
        super("Window", "window.png", KeyEvent.VK_W);
        fixedController = swing.getBusinessController(FixedElementController.class);
    }
    
    @Override
    public boolean mouseReleased(MouseEvent evt, InteractiveRoomMap map) {
        if(position == null)
            return false;
        
        int length = getLength(map.normDiscretize(evt.getPoint()));
        
        if(length == 0)
            return true;
        
        int displacement = getDisplacement(length);
        
        fixedController.addWindow(wallWhere.toString(false), pad(displacement), pad(Math.abs(length)));
        
        return true;
    }
    
    @Override 
    public boolean mouseDragged(MouseEvent evt, InteractiveRoomMap map) {
        if(position == null)
            return false;
        
        int length = getLength(map.normDiscretize(evt.getPoint()));
        
        if(length == 0)
            return true;
        
        int displacement = getDisplacement(length);
        
        map.previewWindow(wallWhere, pad(displacement), pad(Math.abs(length)));
        return true;
    }
}
