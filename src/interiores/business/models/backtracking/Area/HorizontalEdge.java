/*
 * HorizontalEdge represents a horizonal segment which unites two vertexs of an
 * orthogonal Polygon.
 */
package interiores.business.models.backtracking.Area;


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
     * returns whether e is part of this edge, assuming they have
     * the same y coordinate.
     * @param e
     * @return 
     */
    boolean contain(HorizontalEdge e) {
        return xh >= e.xh && xl <= e.xl;
    }
}
