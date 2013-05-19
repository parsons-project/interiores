/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.backtracking.orthogonalArea2;

import interiores.business.models.Orientation;
import java.awt.Point;

/**
 *
 * @author nil.mamano
 */
public class Ray {
    public Point origin;
    public Orientation direction;

    public Ray(Point origin, Orientation direction) {
        this.origin = origin;
        this.direction = direction;
    }
}
