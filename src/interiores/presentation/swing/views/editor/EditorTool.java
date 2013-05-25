package interiores.presentation.swing.views.editor;

import interiores.presentation.swing.views.map.InteractiveRoomMap;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author hector
 */
abstract public class EditorTool
{
    public boolean mouseClicked(MouseEvent evt, InteractiveRoomMap map) {
        return false;
    }
    
    public boolean mousePressed(MouseEvent evt, InteractiveRoomMap map) {
        return false;
    }
    
    public boolean mouseMoved(MouseEvent evt, InteractiveRoomMap map) {
        return false;
    }
    
    public boolean mouseDragged(MouseEvent evt, InteractiveRoomMap map) {
        return false;
    }
    
    public boolean keyReleased(KeyEvent evt, InteractiveRoomMap map) {
        return false;
    }
}
