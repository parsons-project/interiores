/*
 * Orthogonal Polygon represents a special kind of polygon. Which has the following
 * special properties:
 * All its vertexs have discrete coordinates
 * All its edges are either horizontal or vertical (thus, the name)
 * No edges can intersect (except in a single point).
 * It might have holes.
 * 
 * Points over the left and top edges are considered within the polygon,
 * whereas points over the right and bottom edges are not.
 * A point over a vertex which unites a top and a right edge is not considered
 * within the polygon. Similarly for points over a vertex which unites a bottom
 * and a left edge. 
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
class OrthogonalPolygon {
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
     * Returns whether a point is contained in the polygon or above any of its
     * edges or vertexs.
     * @param point
     * @return 
     */
    boolean strongContains(Point point) {
        
        if (! contains(point)) return false;
         
        for (VerticalEdge edge : verticalEdges)
            if (! edge.strongContains(point)) return false;
        
        for (HorizontalEdge edge : horizontalEdges) {
            if (! edge.strongContains(point)) return false;
        }
        
        return true;
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
        if (edgesIntersect(p)) return false;
        
        //3) check that no vertex of the implicit parameter is within the area
        //of the polygon
        //NOTE: this is not needed as a polygon can not have disjoint parts
        //for (Point point : points)
        //    if (p.contains(point)) return false;

        //checking done: p is contained
        return true;
    }
        
    
    /**
     * Given that p and the implicit parameter (i.p.) are not disjoint, adds the
     * area of the polygon p which is not already contained to the area of the
     * i.p. to the area of the i.p.
     * @param p 
     */
    void add(OrthogonalPolygon p) {
        List<Point> newPolygonPoints = new ArrayList<Point>();
        
        //1) find vextexs that are not contained in both polygons
        for (Point myPoint : points)
            if (! p.strongContains(myPoint)) newPolygonPoints.add(myPoint);
        
        for (Point pPoint : points)
            if (! strongContains(pPoint)) newPolygonPoints.add(pPoint);
        
        //2) find intersections between the i.p. and p
        newPolygonPoints.addAll(getEdgesIntersect(p));
        
        //3) for each pair of edges such that one belongs to the i.p. and the
        // other belongs to p and that share more than one point, add the
        // ends of the shared segment such that that end was not a vertex of
        //either of the polygons
        List<Point> endingPoints = getEndingPointsOfSharedSegmentOfNonDisjointEdges(p);
        
        for (Point point : endingPoints)
            if (! points.contains(point) && ! p.points.contains(point))
                newPolygonPoints.add(point);
        
        //this polygon now has all the points of the new polygon.
        //merge completed.
        points = newPolygonPoints;
        
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



    /**
     * Two polygons are considered disjoint if one is not contained inside the
     * other and none of their edges intersect nor share more than one point.
     * @param p
     * @return 
     */
    boolean disjoint(OrthogonalPolygon p) {
        if (contains(p)) return false;
        if (p.contains(this)) return false;
        if (edgesIntersect(p)) return false;
        if (! edgesDisjoint(p)) return false;
        
        return true;
    }
    
    /**
     * Returns whether 2 edges of opposed kind form a cross somewhere in the
     * plane.
     * @param p
     * @return 
     */
    boolean edgesIntersect(OrthogonalPolygon p) {
        synchronizeEdges();

        for (VerticalEdge myEdge : verticalEdges)
            for (HorizontalEdge pEdge : p.horizontalEdges)
                if (myEdge.intersects(pEdge)) return true;
        
        for (HorizontalEdge myEdge : horizontalEdges)
            for (VerticalEdge pEdge : p.verticalEdges)
                if (myEdge.intersects(pEdge)) return true;
        
        return false;
    }
    
    /**
     * Returns all points where 2 edges of opposed kinds form a cross in the
     * plane.
     * @param p
     * @return 
     */
    List<Point> getEdgesIntersect(OrthogonalPolygon p) {
        synchronizeEdges();

        List<Point> intersectionPoints = new ArrayList<Point>();
        
        for (VerticalEdge myEdge : verticalEdges)
            for (HorizontalEdge pEdge : p.horizontalEdges)
                if (myEdge.intersects(pEdge))
                    intersectionPoints.add(myEdge.getIntersection(pEdge));

        for (HorizontalEdge myEdge : horizontalEdges)
            for (VerticalEdge pEdge : p.verticalEdges)
                if (myEdge.intersects(pEdge))
                    intersectionPoints.add(myEdge.getIntersection(pEdge));
        
        return intersectionPoints;
    }
    
    /**
     * Return whether 2 edges of the same kind share more than a point.
     * @param p
     * @return 
     */
    private boolean edgesDisjoint(OrthogonalPolygon p) {
        synchronizeEdges();
        
        for (VerticalEdge myEdge : verticalEdges)
            for (VerticalEdge pEdge : p.verticalEdges)
                if (! myEdge.disjoint(pEdge)) return false;
        
        for (HorizontalEdge myEdge : horizontalEdges)
            for (HorizontalEdge pEdge : p.horizontalEdges)
                if (! myEdge.disjoint(pEdge)) return false;
        
        return true;
    }

    
    private List<Point> getEndingPointsOfSharedSegmentOfNonDisjointEdges(
                            OrthogonalPolygon p) {
        synchronizeEdges();
        
        List<Point> endingPoints = new ArrayList<Point>();
        
        for (VerticalEdge myEdge : verticalEdges)
            for (VerticalEdge pEdge : p.verticalEdges)
                if (! myEdge.disjoint(pEdge))
                    endingPoints.addAll(
                            myEdge.getEndingPointsOfSharedSegment(pEdge));
                    
        
        for (HorizontalEdge myEdge : horizontalEdges)
            for (HorizontalEdge pEdge : p.horizontalEdges)
                if (! myEdge.disjoint(pEdge))
                    endingPoints.addAll(
                            myEdge.getEndingPointsOfSharedSegment(pEdge));
                    
        return endingPoints;
        
    }

}
