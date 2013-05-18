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
    
    public void add(OrthogonalArea area) {
        for (OrthogonalPolygon p : area.polygons) add(p);
    }
    
    public void remove(OrthogonalArea area) {
        for (OrthogonalPolygon p : area.polygons) remove(p);
    }
    
    /**
     * Adds the polygon p to the area.
     * Adding a polygon might result in less disjoint polygons.
     * @param p 
     */
    private void add(OrthogonalPolygon p) {
        Iterator<OrthogonalPolygon> it = polygons.iterator();
        
        //we are going to move to a list all polygons that will be merged into one 
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
    

    /**
     * Removes the area of a polygon from the actual area.
     * Removing a polygon might result in either more or less disjoint polygons.
     * @param p 
     */
    private void remove(OrthogonalPolygon p) {
        Iterator<OrthogonalPolygon> it = polygons.iterator();
        
        //we are going to move to a list all polygons that will be modified
        //due to the remove opperation
        List<OrthogonalPolygon> affectedPolygons = new ArrayList<OrthogonalPolygon>();
        while (it.hasNext()) {
            OrthogonalPolygon myPolygon = it.next();
            if (p.contains(myPolygon)) {
                //this polygon is entirely deleted
                it.remove();
            }
            else if (! myPolygon.disjoint(p)) {
                affectedPolygons.add(myPolygon);
                it.remove();
            }
        }
        
        //for each polygon in affectedPolygons, after the remove operations
        //there might be 1..n polygons.
        for (OrthogonalPolygon myPolygon : affectedPolygons)
            polygons.addAll(myPolygon.resultingPolygonsFromCut(p));
        
    }
}
