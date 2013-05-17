/*
 * HorizontalEdge represents a horizonal segment which unites two vertexs of an
 * orthogonal Polygon.
 */
package interiores.business.models.backtracking.orthogonalArea;

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
     * Returns whether a vertical line x=x intersects with the edge.
     * Touching the line with an ending point of the edge is considered
     * intersecting.
     * @param x x
     * @return 
     */
    boolean contains(int x) {
        return x <= xh && x >= xl;
    }

    boolean intersects(VerticalEdge edge) {
        return edge.x <= xh && edge.x >= xl && y <= edge.yh && y >= edge.yl;
    }
    
}
