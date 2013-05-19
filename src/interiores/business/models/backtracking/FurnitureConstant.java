package interiores.business.models.backtracking;

import interiores.business.models.constraints.UnaryConstraint;
import interiores.shared.backtracking.Value;
import interiores.shared.backtracking.Variable;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author hector
 */
public class FurnitureConstant
    extends InterioresVariable
{
    public FurnitureConstant(String identifier, FurnitureValue value) {
        super(identifier);
        assignValue(value);
        
        domain = Domain.empty();
    }
    
    @Override
    public void resetIterators(int iteration) {
        // IM CONSTANT :D
    }
    
    @Override
    public Value getNextDomainValue() {
        return getAssignedValue();
    }  
    
    @Override
    public boolean hasMoreValues() {
        return false;
    }

    @Override
    public final void assignValue(Value value) {
        if(isAssigned())
            throw new UnsupportedOperationException("You cannot reassign a constant value.");
        
        assignValue(value);
    }

    
    @Override
    public void undoAssignValue() {
        throw new UnsupportedOperationException("You cannot unassign a constant value.");    
    }
  
    @Override
    public void trimDomain(Variable variable, int iteration) {
        // IM CONSTANT :D
    }

    @Override
    public void undoTrimDomain(Variable variable, Value value, int iteration) {
        // IM CONSTANT :D
    }

    @Override
    public boolean isAssigned() {
        return true;
    }
    
    @Override
    public Collection<UnaryConstraint> getUnaryConstraints() {
        return new ArrayList(); // Empty :O
    }
}
