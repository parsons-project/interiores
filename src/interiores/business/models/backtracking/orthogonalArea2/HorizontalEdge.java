/*
 * HorizontalEdge represents a horizonal segment which unites two vertexs of an
 * orthogonal Polygon.
 */
package interiores.business.models.backtracking.orthogonalArea;

import interiores.business.models.Orientation;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nil.mamano
 */
class HorizontalEdge {
    public int y;
    public int xh;
    public int xl;

    public HorizontalEdge(int y, int xh, int xl) {
        this.y = y;
        this.xh = xh;
        this.xl = xl;
    }
    
    /**
     * @param ray
     * @return 
     */
    boolean intersects(Ray ray) {
        if (ray.direction == Orientation.E)
            return y > ray.origin.y && xl <= ray.origin.x && xh > ray.origin.x;
        else if (ray.direction == Orientation.W)
            return y <= ray.origin.y && xl <= ray.origin.x && xh > ray.origin.x;
        else return false;
    }
    
    /**
     * 
     * @param ray
     * @return 
     */
    boolean intersects(GridRay ray) {
        if (ray.direction == Orientation.E)
            return y > ray.origin.y && xl < ray.origin.x && xh > ray.origin.x;
        else if (ray.direction == Orientation.W)
            return y < ray.origin.y && xl < ray.origin.x && xh > ray.origin.x;
        else return false;
    }
    
    boolean intersects(VerticalEdge edge) {
        return y > edge.yl && y < edge.yh && edge.x > xl && edge.x < xh;
    }
    
    //=========================================================================
    

    /**
     * Returns whether 2 HorizontalEdges share more than a single point.
     * @param p the edge which might be disjoint.
     * @return true if the edges are disjoints.
     */
    boolean disjoint(HorizontalEdge p) {
        return y == p.y && (
                (p.xl >= xl && p.xl <  xh) ||
                (p.xh >  xl && p.xh <= xh)
               );
    }
    
    
    /**
     * Returns whether a point is contained in the edge.
     * A point over either of the edges is considered contained.
     * @param point
     * @return 
     */
    boolean strongContains(Point p) {
        return y == p.y && p.x <= xh && p.x >= xl;
    }
    
    
    /**
     * Given that the implicit parameter and pEdge intersect, returns the
     * point of the intersection.
     * @param pEdge
     * @return 
     */
    GridPoint getIntersection(VerticalEdge pEdge) {
        return new GridPoint(y, pEdge.x);
    }

    /**
     * Given that the implicit parameter and p share a common segment,
     * returns the two ends of this segment.
     * @param p
     * @return 
     */
    List<Point> getEndingPointsOfSharedSegment(HorizontalEdge p) {
        
        List<Point> endingPoints = new ArrayList<Point>();
        
        Point pLeftPoint = p.leftPoint();
        if (strongContains(pLeftPoint)) endingPoints.add(pLeftPoint);
        else endingPoints.add(leftPoint());
        
        Point pRightPoint = p.rightPoint();
        if (strongContains(pRightPoint)) endingPoints.add(pRightPoint);
        else endingPoints.add(rightPoint());
        
        return endingPoints;
    }

    private Point leftPoint() {
        return new Point(xl, y);
    }

    private Point rightPoint() {
        return new Point(xh, y);
    }

    /**
     * Assuming p is one of the ending points of the edge, returns the other
     * ending point.
     * @param p
     * @return 
     */
    Point getOtherVertex(Point p) {
        if (p.equals(leftPoint())) return rightPoint();
        else return leftPoint();
    }
    
    
    
}
