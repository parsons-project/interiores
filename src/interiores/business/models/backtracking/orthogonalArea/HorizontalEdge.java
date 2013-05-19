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
    
}
