

package interiores.business.models.constraints.furniture;

import interiores.business.models.backtracking.FurnitureVariable;

/**
 * A constraint implementing this interface has to check if it is satisfied
 * before assigning a value to its associated variable.
 * @author hector
 */
public interface InexhaustiveTrimmer
{
    public abstract boolean isSatisfied(FurnitureVariable variable);

}
