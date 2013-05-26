package interiores.business.models.backtracking;

import interiores.shared.backtracking.Value;
import interiores.shared.backtracking.Variable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hector
 */
@XmlRootElement
public class FurnitureConstant
    extends InterioresVariable
{
    public FurnitureConstant()
    { }
    
    public FurnitureConstant(String typeName, FurnitureValue value) {
        super(typeName);
        
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
