/*
 * VerticalEdge represents a vertical segment which unites two vertexs of an
 * orthogonal Polygon.
 */
package interiores.business.models.backtracking.Area;

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
     * @param ray
     * @return 
     */
    boolean intersects(RightRay ray) {
        return x > ray.origin.x && yl <= ray.origin.y && yh > ray.origin.y;
    }
    
    boolean intersects(HorizontalEdge edge) {
        return x > edge.xl && x < edge.xh && edge.y > yl && edge.y < yh;
    }
    
    /**
     * Given that the implicit parameter and p intersect, returns the
     * point of the intersection.
     * @param p
     * @return 
     */
    GridPoint getIntersection(HorizontalEdge p) {
        return new GridPoint(x, p.y);
    }

    /**
     * returns whether e is part of this edge, assuming they have
     * the same x coordinate.
     * @param e
     * @return 
     */
    boolean contain(VerticalEdge e) {
        return yh >= e.yh && yl <= e.yl;
    }
        
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("(" + x + "," + yh + ".." + yl + ")");
        return result.toString();
    }
    
    @Override
    public boolean equals(Object p) {
        return this.x == ((VerticalEdge)p).x && this.yl == ((VerticalEdge)p).yl
                && this.yh == ((VerticalEdge)p).yh;
    }
}
