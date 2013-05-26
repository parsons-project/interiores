package interiores.presentation.swing.views.editor.tools;

import interiores.presentation.swing.views.map.InteractiveRoomMap;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.net.URL;

/**
 *
 * @author hector
 */
abstract public class EditorTool
{
    private String name;
    private URL iconURL;
    private int keyShortcut;
    
    public EditorTool(String name, String iconPath, int keyShortcut) {
        this.name = name;
        this.iconURL = getClass().getResource("/resources/" + iconPath);
        this.keyShortcut = keyShortcut;
    }
    
    public String getName() {
        return name;
    }
    
    public URL getIconURL() {
        return iconURL;
    }
    
    public int getKeyShortcut() {
        return keyShortcut;
    }
    
    public String getToolTipText() {
        return getName() + " tool";
    }
    
    public boolean mouseClicked(MouseEvent evt, InteractiveRoomMap map) {
        return false;
    }
    
    public boolean mousePressed(MouseEvent evt, InteractiveRoomMap map) {
        return false;
    }
    
    public boolean mouseReleased(MouseEvent evt, InteractiveRoomMap map) {
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
