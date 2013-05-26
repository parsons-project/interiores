package interiores.presentation.swing.views.editor.tools;

import interiores.business.controllers.FixedElementController;
import interiores.business.models.Orientation;
import interiores.presentation.swing.views.map.InteractiveRoomMap;
import interiores.presentation.swing.views.map.RoomMap;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 *
 * @author alvaro
 */
public class OnWallElementTool 
    extends EditorTool 
{
    protected Point position;
    protected Orientation wallWhere;
    protected FixedElementController fixedController;
    
    public OnWallElementTool(String name, String iconPath, int keyShortcut) {
        super(name, iconPath, keyShortcut);
        
    }
    
    @Override
    public boolean mousePressed(MouseEvent evt, InteractiveRoomMap map) {
        position  = map.normDiscretize(evt.getPoint());
        wallWhere = map.getNearestWall(evt.getX(), evt.getY());
   
        return false;
    }
    
    protected int getLength(Point normPos) {
        if (wallWhere == Orientation.E || wallWhere == Orientation.W)
            return normPos.y - position.y;

        return normPos.x - position.x;
    }
    
    protected int pad(int distance) {
        return distance - distance % RoomMap.getResolution();
    }
    
    protected int getDisplacement(int length) {
        Point p = new Point(position);
        if (length < 0) {
            p.x += length;
            p.y += length;
        }
        if (wallWhere == Orientation.E || wallWhere == Orientation.W)
            return p.y - RoomMap.getPadding();
            
        return p.x - RoomMap.getPadding();
    }
    
}
