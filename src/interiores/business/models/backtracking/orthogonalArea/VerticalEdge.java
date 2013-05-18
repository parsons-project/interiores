/*
 * VerticalEdge represents a vertical segment which unites two vertexs of an
 * orthogonal Polygon.
 */
package interiores.business.models.backtracking.orthogonalArea;

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
}
