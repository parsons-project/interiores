/*
 * VerticalEdge represents a vertical segment which unites two vertexs of an
 * orthogonal Polygon.
 */
package interiores.business.models.backtracking.orthogonalArea2;

import interiores.business.models.backtracking.orthogonalArea.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nil.mamano
 */
class VerticalEdge {
    
    //the x coordinate shared by the 2 vertexs of the segment
    public int x;
    //as the Y axis increases downwards, this is the bottom vertex of the segment
    public int yh;
    //as the Y axis increases downwards, this is the top vertex of the segment
    public int yl;    

    public VerticalEdge(int x, int yh, int yl) {
        this.x = x;
        this.yh = yh;
        this.yl = yl;
    }

    
    
    /**
     * Returns whether a horizontal line y=y intersects with the edge.
     * The superior vertex is considered contained in the polygon, while the
     * inferiore vertex is not.
     * @param y y
     * @return 
     */
    boolean contains(int y) {
        return y < yh && y >= yl;
    }

    /**
     * Two edges intersect if they form a cross at some point in the plane.
     * @param edge
     * @return 
     */
    boolean intersects(HorizontalEdge edge) {
        return edge.y < yh && edge.y > yl && x < edge.xh && x > edge.xl;
    }

    /**
     * Returns whether 2 verticalEdges share more than a single point.
     * @param p the edge which might be disjoint.
     * @return true if the edges are disjoints.
     */
    boolean disjoint(VerticalEdge p) {
        return x == p.x && (
                (p.yl >= yl && p.yl <  yh) ||
                (p.yh >  yl && p.yh <= yh)
               );
    }

    /**
     * Returns whether a point is contained in the edge.
     * A point over either of the edges is considered contained.
     * @param p
     * @return 
     */
    boolean strongContains(Point p) {
        return x == p.x && p.y <= yh && p.y >= yl;
    }

    /**
     * Given that the implicit parameter and p intersect, returns the
     * point of the intersection.
     * @param p
     * @return 
     */
    Point getIntersection(HorizontalEdge p) {
        return new Point(x, p.y);
    }

    /**
     * Given that the implicit parameter and p share a common segment,
     * returns the two ends of this segment.
     * @param p
     * @return 
     */
    List<Point> getEndingPointsOfSharedSegment(VerticalEdge p) {
        
        List<Point> endingPoints = new ArrayList<Point>();
        
        Point pTopPoint = p.topPoint();
        if (strongContains(pTopPoint)) endingPoints.add(pTopPoint);
        else endingPoints.add(topPoint());
        
        Point pBottomPoint = p.bottomPoint();
        if (strongContains(pBottomPoint)) endingPoints.add(pBottomPoint);
        else endingPoints.add(bottomPoint());
        
        return endingPoints;
    }
    

    private Point topPoint() {
        return new Point(x, yl);
    }

    private Point bottomPoint() {
        return new Point(x, yh);
    }

    /**
     * Assuming p is one of the ending points of the edge, returns the other
     * ending point.
     * @param p
     * @return 
     */
    Point getOtherVertex(Point p) {
        if (p.equals(topPoint())) return bottomPoint();
        else return topPoint();
    }
}
