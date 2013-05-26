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
public class WindowTool 
    extends EditorTool 
{
    private SwingController swing;
    private FixedElementController fixedController;
    private Point position;
    private Orientation wallWhere;
    
    public WindowTool(SwingController swing) {
        super("Window", "window.png", KeyEvent.VK_W);
        this.swing = swing;
        fixedController = swing.getBusinessController(FixedElementController.class);
    }
    
    @Override
    public boolean mouseReleased(MouseEvent evt, InteractiveRoomMap map) {
        Point p = map.normDiscretize(evt.getPoint());

        if (position != null) {
            Point posDisNorm = map.normDiscretize(position);
            int distance = (int) posDisNorm.distance(p);
            switch (wallWhere) {
                case E:
                case W:
                    fixedController.addWindow(wallWhere.toString(false), posDisNorm.y - RoomMap.getPadding(),
                            distance);
                    break;
                case N:
                case S:
                    fixedController.addWindow(wallWhere.toString(false), posDisNorm.x - RoomMap.getPadding(),
                            distance);
                    break;
            }
            return true;
        }
        return false;
    }
    
    @Override 
    public boolean mouseDragged(MouseEvent evt, InteractiveRoomMap map) {
        return false;
    }
    
    @Override
    public boolean mousePressed(MouseEvent evt, InteractiveRoomMap map) {
        position  = new Point(evt.getX(), evt.getY());
        wallWhere = map.getNearestWall(position.x, position.y);
   
        return false;
    }
}
