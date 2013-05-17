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
    public int x;
    public int yh;
    public int yl;    

    public VerticalEdge(int x, int yh, int yl) {
        this.x = x;
        this.yh = yh;
        this.yl = yl;
    }

    
    
    /**
     * Returns whether a horizontal line y=y intersects with the edge.
     * Touching the line with an ending point of the edge is considered
     * intersecting.
     * @param y y
     * @return 
     */
    boolean contains(int y) {
        return y <= yh && y >= yl;
    }

    boolean intersects(HorizontalEdge edge) {
        return edge.y <= yh && edge.y >= yl && x <= edge.xh && x >= edge.xl;
    }
}
