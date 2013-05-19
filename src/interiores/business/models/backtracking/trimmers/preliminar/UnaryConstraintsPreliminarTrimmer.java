/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.backtracking.trimmers.preliminar;

import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.backtracking.trimmers.PreliminarTrimmer;
import interiores.business.models.constraints.UnaryConstraint;

/**
 *
 * @author hector0193
 */
public class UnaryConstraintsPreliminarTrimmer
    implements PreliminarTrimmer
{
    @Override
    public void preliminarTrim(FurnitureConstant[] constants, FurnitureVariable[] variables)
    {
        //1) remove values which do not fit some unary constraint
        for (FurnitureVariable variable : variables)
            applyUnaryConstraints(variable);
    }
    
    private void applyUnaryConstraints(FurnitureVariable variable)
    {
        for(UnaryConstraint constraint : variable.getUnaryConstraints())
            constraint.eliminateInvalidValues(variable.getDomain());
    }
}
