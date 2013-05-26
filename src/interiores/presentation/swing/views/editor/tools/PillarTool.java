package interiores.presentation.swing.views.editor.tools;

import interiores.business.controllers.FixedElementController;
import interiores.business.models.Orientation;
import interiores.core.presentation.SwingController;
import interiores.presentation.swing.views.editor.EditorTool;
import interiores.presentation.swing.views.map.InteractiveRoomMap;
import interiores.presentation.swing.views.map.RoomMap;
import interiores.utils.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 *
 * @author alvaro
 */
public class PillarTool
    extends EditorTool
{
    private FixedElementController fixedController;
    private Point position;
    
    public PillarTool(SwingController swing) {
        fixedController = swing.getBusinessController(FixedElementController.class);
    }
    
    @Override
    public boolean mouseReleased(MouseEvent evt, InteractiveRoomMap map) {
        Point p = map.unpad(map.normDiscretize(evt.getPoint()));

        if (position != null) {
            fixedController.addPillar(position, new Dimension((p.x - position.x), (p.y - position.y)));
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
        position  = map.unpad(map.normDiscretize(evt.getPoint()));
        return false;
    }
}
