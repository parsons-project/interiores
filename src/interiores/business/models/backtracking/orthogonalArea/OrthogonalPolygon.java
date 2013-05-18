/*
 * Orthogonal Polygon represents a special kind of polygon. Which has the following
 * special properties:
 * All its vertexs have discrete coordinates
 * All its edges are either horizontal or vertical (thus, the name)
 * No edges can intersect (except in a single point).
 * It might have holes.
 */
package interiores.business.models.backtracking.orthogonalArea;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nil.mamano
 */
public class OrthogonalPolygon {
    List<Point> points;
    
    /**
     * Given a x' value, permits fast access to all points of the polygon such
     * that they are of the form (x,y), where x=x'.
     */
    private HashMap<Integer,List<Point>> pointsStoredByX;
    /**
     * Given a y' value, permits fast access to all points of the polygon such
     * that they are of the form (x,y), where y=y'.
     */
    private HashMap<Integer,List<Point>> pointsStoredByY;
    
    List<VerticalEdge> verticalEdges;
    List<HorizontalEdge> horizontalEdges;
    
    /**
     * Represents whether the edges are synchronized with the set of
     * points.
     */
    private boolean edgesSynchronized;

    /**
     * Constructor from a list of points. Those points must form a valid
     * OrthogonalPolygon.
     * @param points 
     */
    public OrthogonalPolygon(List<Point> points) {
        this.points = points;
        
        //initialize Maps of positions
        for (Point p : points) {
            if (!pointsStoredByX.containsKey(p.x))
                pointsStoredByX.put(p.x, new ArrayList<Point>());
            pointsStoredByX.get(p.x).add(p);
            
            if (!pointsStoredByY.containsKey(p.y))
                pointsStoredByY.put(p.y, new ArrayList<Point>());
            pointsStoredByY.get(p.y).add(p);
        }
        
        edgesSynchronized = false;
    }

    
    
    /**
     * Returns whether a point is contained in the polygon.
     * @param point the point which might be contained.
     * @return true if point is contained in the polygon 
     */
    boolean contains(Point point) {
        
        //we will operate with edges. Thus, we must make sure they are synchronized
        synchronizeEdges();
        
        //apply ray-casting algorithm; direction: positive side of the x axis
        int intersectionCount = 0;
        for (VerticalEdge edge : verticalEdges) {
            if (edge.contains(point.y)) ++intersectionCount;
        }
        
        //return result according to the odd-even rule
        return (intersectionCount % 2) == 1;
    }
    

    /**
     * Returns whether a orthogonal polygon is contained in the polygon.
     * @param polygon the polygon which might be contained.
     * @return true if polygon is contained in the implicit parameter
     */
    boolean contains(OrthogonalPolygon p) {
        
        //1) check whether at least one point is contained
        if (! contains(p.points.get(0)))
            return false;
        
        //2) check that no edge of the polygon intersects with an edge of the
        //implicit parameter
        synchronizeEdges();

        for (VerticalEdge myEdge : verticalEdges) {
            for (HorizontalEdge pEdge : p.horizontalEdges) {
                if (myEdge.intersects(pEdge)) return false;
            }
        }
        
        for (HorizontalEdge myEdge : horizontalEdges) {
            for (VerticalEdge pEdge : p.verticalEdges) {
                if (myEdge.intersects(pEdge)) return false;
            }
        }
        
        //3) check that no vertex of the implicit parameter is within the area
        //of the polygon
        for (Point point : points)
            if (p.contains(point)) return false;

        //checking done: p is contained
        return true;
    }
        
        
        
    /**
     * Synchronizes the edges with the points.
     */
    private void synchronizeEdges() {
        if (!edgesSynchronized) {
            
            //restart edges
            verticalEdges = new ArrayList<VerticalEdge>();
            horizontalEdges = new ArrayList<HorizontalEdge>();
            
            for(Point point : points) {
                
                //vertical edge
                int VerticalCount = 0;
                Point closestPoint = null;
                //count how many points with the same x value how a higher y value
                //and store the closest point
                for(Point p : pointsStoredByX.get(point.x)) {
                    if (p.y > point.y) {
                        ++VerticalCount;
                        //update the closest point, if apropiate
                        if (closestPoint == null || closestPoint.y > p.y)
                            closestPoint = p;
                    }
                }
                //if there was an odd number of points, there is a vertical
                //edge between point and closestPoint
                verticalEdges.add(new VerticalEdge(point.x, closestPoint.y, point.y));
                
                //horizontal edge
                int HorizontalCount = 0;
                closestPoint = null;
                //count how many points with the same x value how a higher y value
                //and store the closest point
                for(Point p : pointsStoredByY.get(point.y)) {
                    if (p.x > point.x) {
                        ++HorizontalCount;
                        //update the closest point, if apropiate
                        if (closestPoint == null || closestPoint.x > p.x)
                            closestPoint = p;
                    }
                }
                //if there was an odd number of points, there is a horizontal
                //edge between point and closestPoint
                horizontalEdges.add(new HorizontalEdge(point.y, closestPoint.x, point.x));                
            }
            
            //we are done. Thus, edges are now synchronized
            edgesSynchronized = true;
        }
    }


}
