

package interiores.business.models.constraints.furniture;

import interiores.business.models.backtracking.FurnitureVariable;
import interiores.core.business.BusinessException;
import java.util.Map;

/**
 *
 * @author hector
 */
interface InexhaustiveConstraint
{
    public abstract boolean isSatisfied(FurnitureVariable variable);

}
