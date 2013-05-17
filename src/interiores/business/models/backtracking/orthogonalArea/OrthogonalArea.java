/*
 * An OrthogonalArea represents a set of discretized square surface units.
 * This is represented internely with a set of OrthogonalPolygons.
 */
package interiores.business.models.backtracking.orthogonalArea;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nil.mamano
 */
public class OrthogonalArea {
    private List<OrthogonalPolygon> polygons;
    
    /**
     * Default constructor.
     */
    public OrthogonalArea() {
        polygons = new ArrayList<OrthogonalPolygon>();
    }
    
    /**
     * Returns whether a given point is within the area.
     * A point over an edge or vertex of the area is considered within.
     */
    public boolean contains(Point point) {
        for (OrthogonalPolygon polygon : polygons)
            if (polygon.contains(point)) return true;
        return false;
    }
    
    /**
     * Returns whether a given orthogonal polygon is within the area.
     * A point over an edge or vertex of the area is considered within.
     */
    public boolean contains(OrthogonalPolygon p) {
        for (OrthogonalPolygon polygon : polygons)
            if (polygon.contains(p)) return true;
        return false;        
    }
}
