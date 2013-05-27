/**
 * Represents a square (that is, a elemental surface unit) of the Grid Plane.
 */
package interiores.business.models.backtracking.area;

/**
 *
 * @author Nil
 */
class Square {
    int x;
    int y;
    
    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean equals(Object p) {
        return this.x == ((Square)p).x && this.y == ((Square)p).y;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.x;
        hash = 67 * hash + this.y;
        return hash;
    }
}
