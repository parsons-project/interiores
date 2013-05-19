
package interiores.business.models.backtracking.orthogonalArea;

import interiores.business.models.Orientation;
import java.awt.Point;

/**
 *
 * @author nil.mamano
 */
class Ray {
    public Point origin;
    public Orientation direction;

    public Ray(Point origin, Orientation direction) {
        this.origin = origin;
        this.direction = direction;
    }
}
