package interiores.presentation.swing.views.map;

import interiores.business.models.Orientation;
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
    
    public boolean select(int x, int y) {
        Point p = normalize(x, y);
        
        Debug.println("Selecting element at " + p);
        
        RoomElement element = getElementAt(p.x, p.y);
        
        if(element != null) {
            element.select();
            selected.add(element);
            return true;
        }
        return false;
    }
    
    public boolean unselect(int x, int y) {
        Point p = normalize(x, y);
        
        Debug.println("Unselecting element at " + p);
        
        RoomElement element = getElementAt(p.x, p.y);
        
        if(element != null) {
            element.unselect();
            selected.remove(element);
            return true;
        }
        return false;
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
    
    public String getLastSelected() {
        return selected.get(getSelectedSize() - 1).getName();
    }
    
    public int getSelectedSize() {
        return selected.size();
    }
    
    /**
     * Translates the selected furniture using a displacement
     * represented for the vector with points a-b.
     * @param a Start point
     * @param b End point
     */
    public boolean translateSelected(Point a, Point b) {
        a = normDiscretize(a);
        b = normDiscretize(b);
        
        int dx = b.x - a.x;
        int dy = b.y - a.y;
        
        if(dx != 0 || dy != 0) {
            translateSelected(dx, dy);
            return true;
        }
        
        return false;
    }
    
    public void translateSelected(int dx, int dy) {
        for(RoomElement element : selected)
            element.translate(dx, dy);
    }
    
    public int getDistanceToWall(Orientation orientation, Point p) {
        switch (orientation) {
            case N:
                return Math.abs(PADDING - p.y);
            case S:
                return Math.abs(PADDING + depth - p.y);
            case W:
                return Math.abs(PADDING - p.x);
            case E:
                return Math.abs(PADDING + width - p.x);
            default:
                return -1;
        }
    }
    
    public Orientation getNearestWall(int x, int y) {
        Point p = normalize(x, y);
        Orientation orientation  = Orientation.N;
        int distance = getDistanceToWall(Orientation.N, p);
        int curDistance;
        for (Orientation o : Orientation.values()) {
            curDistance = getDistanceToWall(o, p);
            if (distance > curDistance) {
                orientation = o;
                distance = curDistance;
            }
        }
        return orientation;
    }
    
    public Point normalize(int x, int y) {
        return new Point((int)(x / SCALE), (int)(y / SCALE));
    }
    
    public Point normalize(Point p) {
        return normalize(p.x, p.y);
    }
    
    public Point discretize(Point p) {
        Point q = new Point(p.x, p.y);
        
        q.x -= q.x % RESOLUTION;
        q.y -= q.y % RESOLUTION;
        
        return q;
    }
    
    /**
     * Normalizes and discretizes a given point.
     * @param p
     * @return 
     */
    public Point normDiscretize(Point p) {
        return discretize(normalize(p));
    }
}
