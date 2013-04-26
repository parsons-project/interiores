package interiores.shared;

/**
 * Position represents a unit of space in a surface. A position consists of 2 coordinates.
 */
public class Position {

    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isEqual(Position p) {
        return x == p.x && y == p.y;
    }

    public boolean isSmaller(Position p) {
        if (x == p.x) return y < p.y;
        else          return x < p.x;
    }

    public Position plus(Position p) {
        return new Position(x + p.x, y + p.y);
    }

    public void incrementRow() {
        this.x += 1;
    }

    public void incrementColumn() {
        this.y += 1;
    }
}