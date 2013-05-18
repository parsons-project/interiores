/*
 * An OrthogonalArea represents a set of discretized square surface units.
 * This is represented internely with a set of OrthogonalPolygons.
 * 
 */
package interiores.business.models.backtracking.orthogonalArea;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
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
    
    /**
     * Adds the polygon p to the area
     * @param p 
     */
    public void add(OrthogonalPolygon p) {
        Iterator<OrthogonalPolygon> it = polygons.iterator();
        //adding a polygon might result in less disjoint polygons
        
        //we are goint to store in a list all polygons that will be merged into one 
        List<OrthogonalPolygon> mergedPolygons = new ArrayList<OrthogonalPolygon>();
        while (it.hasNext()) {
            OrthogonalPolygon myPolygon = it.next();
            if (! myPolygon.disjoint(p)) {
                it.remove();
                mergedPolygons.add(myPolygon);
            }
        }
        
        //now that we have all the polygons that will form a new one, we calculate
        //this new polygon
        for (OrthogonalPolygon myPolygon : mergedPolygons)
            p.add(myPolygon);
        
        //add this new polygon to the list of polygons
        polygons.add(p);
    }
}
