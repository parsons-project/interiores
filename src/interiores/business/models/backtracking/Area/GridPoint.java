
package interiores.business.models.backtracking.Area;

import interiores.core.Debug;

/**
 *
 * @author nil.mamano
 */
class GridPoint {
    int x;
    int y;

    public GridPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("(" + x + "," + y + ")");
        return result.toString();
    }
    
    @Override
    public boolean equals(Object p) {
        Debug.println("In GridPoint.equals");
        Debug.println("This:" + this.toString());
        Debug.println("Param:" + p.toString());

        Boolean result = this.x == ((GridPoint)p).x && this.y == ((GridPoint)p).y;

        Debug.println("Result:" + result.toString());
        return result;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.x;
        hash = 67 * hash + this.y;
        return hash;
    }
}
