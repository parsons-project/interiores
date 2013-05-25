

package interiores.business.models.constraints.furniture;

import interiores.business.models.backtracking.FurnitureVariable;

/**
 *
 * @author hector
 */
public interface InexhaustiveConstraint
{
    public abstract boolean isSatisfied(FurnitureVariable variable);

}
