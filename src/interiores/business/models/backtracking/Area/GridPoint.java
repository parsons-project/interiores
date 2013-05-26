
package interiores.business.models.backtracking.Area;

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
    
}
