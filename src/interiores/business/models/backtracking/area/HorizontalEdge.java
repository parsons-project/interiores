/*
 * HorizontalEdge represents a horizonal segment which unites two vertexs of an
 * orthogonal Polygon.
 */
package interiores.business.models.backtracking.area;


/**
 * Represents a horizontal edge of an Area of the GridPlane.
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
    
    
    @Override
    public boolean equals(Object p) {
        return this.y == ((HorizontalEdge)p).y && this.xl == ((HorizontalEdge)p).xl
                && this.xh == ((HorizontalEdge)p).xh;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.y;
        hash = 23 * hash + this.xh;
        hash = 23 * hash + this.xl;
        return hash;
    }
}
