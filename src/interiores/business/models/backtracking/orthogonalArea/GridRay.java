
package interiores.business.models.backtracking.orthogonalArea;

import interiores.business.models.Orientation;

/**
 *
 * @author nil.mamano
 */
class GridRay {
    public GridPoint origin;
    public Orientation direction;
    
    public GridRay(GridPoint origin, Orientation direction) {
        this.origin = origin;
        this.direction = direction;
    }
}
