package interiores.business.models.backtracking;

import interiores.shared.backtracking.Value;
import interiores.shared.backtracking.Variable;

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
    }
    
    @Override
    public void resetIterators(int iteration) {
        
    }
    
    //Pre: we have not iterated through all domain values yet.
    @Override
    public Value getNextDomainValue() {
        return getAssignedValue();
    }  
    
    //Pre: the 3 iterators point to valid values
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
}
