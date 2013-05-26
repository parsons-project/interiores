package interiores.presentation.swing.views.editor.tools;

import interiores.presentation.swing.views.map.InteractiveRoomMap;
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
    private Point previous;
    
    public MoveTool() {
        super("Move", "move.png", KeyEvent.VK_M);
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
