package interiores.presentation.swing.views.map;

import interiores.core.Debug;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author hector
 */
public class InteractiveRoomMap
    extends RoomMap
{
    private List<RoomElement> selected;
    
    public InteractiveRoomMap(int roomWidth, int roomDepth) {
        super(roomWidth, roomDepth);
        
        selected = new ArrayList();
    }
    
    public void select(int x, int y) {
        Point p = normalize(x, y);
        
        Debug.println("Selecting element at " + p);
        
        RoomElement element = getElementAt(p.x, p.y);
        
        if(element != null) {
            element.select();
            selected.add(element);
        }
    }
    
    public void unselectAll() {
        for(RoomElement element : selected)
            element.unselect();
        
        selected.clear();
    }
    
    public Collection<String> getSelected() {
        Collection<String> names = new TreeSet();
        
        for(RoomElement element : selected)
            names.add(element.getName());
        
        return names;
    }
    
    public Point normalize(int x, int y) {
        return new Point((int)(x / SCALE), (int)(y / SCALE));
    }
}
