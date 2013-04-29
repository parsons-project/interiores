package interiores.business.models.constraints;

import interiores.business.models.backtracking.FurnitureVariable;

/**
 *
 * @author alvaro
 */
public abstract class BinaryConstraint {
    
    public abstract boolean isSatisfied(FurnitureVariable fvariable1, FurnitureVariable fvariable2);
}
