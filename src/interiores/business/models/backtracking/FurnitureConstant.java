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
    public FurnitureConstant(String name, String typeName, FurnitureValue value) {
        super(name, typeName);
        
        assignValue(value);
    }
    
    @Override
    public boolean isConstant() {
        return true;
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
        
        super.assignValue(value);
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
}
