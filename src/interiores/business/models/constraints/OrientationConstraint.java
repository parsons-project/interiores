package interiores.business.models.constraints;

import interiores.business.models.Orientation;
import interiores.business.models.backtracking.FurnitureVariable;
import java.util.List;

/**
 * OrientationConstraint represents a constraint imposed over the orientation of
 * a piece of furniture.
 * The furniture piece will only be able to take an orientation from the list
 * of the constraint.
 */
public class OrientationConstraint
    extends UnaryConstraint {
    
    /**
     * validOrientations contains the orientations a piece of furniture can have
     * in order to satisfy the constraint.
     */
    private List<Orientation> validOrientations;
    
    /**
     * Creates an orientation constraint such that only those pieces of
     * furniture matching one of the given orientations will satisfy it
     * @param validOrientations The orientations that define the constraint
     */
    public OrientationConstraint(List<Orientation> validOrientations) {
        this.validOrientations = validOrientations;
    }
    
    /**
     * Determines whether an orientation satisfies the constraint.
     * @param variable The variable to be checked.
     */
    @Override
    public void eliminateInvalidValues(FurnitureVariable variable) {        
        variable.orientations = validOrientations;
    }
    
    /**
     * Modifies the orientations defined for the constraint.
     * @param newOrientations The orientations that will override the previous ones
     */
    public void changeOrientation(List<Orientation> newOrientations) {
        validOrientations = newOrientations;
    }    
}

