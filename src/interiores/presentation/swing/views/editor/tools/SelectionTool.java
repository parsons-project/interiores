package interiores.presentation.swing.views.editor.tools;

import interiores.core.presentation.SwingController;
import interiores.presentation.swing.views.ConstraintEditorFrame;
import interiores.presentation.swing.views.map.InteractiveRoomMap;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author hector
 */
public class SelectionTool
    extends EditorTool
{
    private SwingController swing;
    
    public SelectionTool(SwingController swing) {
        super("Selection", "cursor.png", KeyEvent.VK_S);
        this.swing = swing;
    }
    
    @Override
    public boolean mousePressed(MouseEvent evt, InteractiveRoomMap map) {
        int x = evt.getX();
        int y = evt.getY();
                
        switch (evt.getButton()) {
            case MouseEvent.BUTTON1: // left click
                if(!evt.isControlDown())
                    map.unselectAll();

                map.select(x, y);
                break;
                
            case MouseEvent.BUTTON3: // right click
                if (map.select(x, y)) {
                    ConstraintEditorFrame cef = swing.getNew(ConstraintEditorFrame.class);
                    cef.setSelectedId(map.getLastSelected());
                    cef.setVisible(true);
                }
                
                map.unselect(x, y);
                break;
        }        
        return true; // Always repaint
    }
}
