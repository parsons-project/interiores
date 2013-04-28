
package interiores.business.models.constraints;

import interiores.business.models.backtracking.FurnitureVariable;

/**
 * Generic class for representing constraints over a furniture piece.
 */
public class UnaryConstraint {
     /**
     * Eliminates all values from the variables' domain that do not fulfil the
     * restriction.
     * @param variable The specific variable that has to pass the constraint
     */
    public abstract void eliminateInvalidValues(FurnitureVariable variable);
}
