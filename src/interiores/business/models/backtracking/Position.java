package interiores.business.models.backtracking;

import java.awt.Point;

/**
* Position represents a unit of space in a surface.
* A position consists of 2 coordinates.
*/
public class Position
    extends Point
{
	public Position (int x, int y) {
            super(x, y);
	}
	
	public boolean isSmaller(Position p) {
		if (x == p.x) return y < p.y;
		return x < p.x;
	}
}
