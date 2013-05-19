package interiores.business.models.backtracking;

import interiores.business.models.constraints.UnaryConstraint;
import interiores.shared.backtracking.Value;
import interiores.shared.backtracking.Variable;
import java.util.Collection;

/**
 *
 * @author hector
 */
abstract public class InterioresVariable implements Variable
{
    private String identifier;
    protected Collection<UnaryConstraint> unaryConstraints;
    protected Domain domain;
    
    /**
    * Represents the value taken by the variable, in case it is assigned.
    * Only valid when isAssigned is true.
    */
    public Value assignedValue;
    private boolean isAssigned;
    
    public InterioresVariable(String idenitifer) {
        this.identifier = idenitifer;
        
        isAssigned = false;
    }
    
    public String getID() {
        return identifier;
    }
    
    @Override
    public boolean isAssigned() {
        return isAssigned;
    }
    
    @Override
    public Value getAssignedValue() {
        return assignedValue;
    }
       
    @Override
    public void assignValue(Value value) {
        isAssigned = true;
        assignedValue = value;
    }

    
    @Override
    public void undoAssignValue() {
        isAssigned = false;
        assignedValue = null;        
    }
    
    public Collection<UnaryConstraint> getUnaryConstraints() {
        return unaryConstraints;
    }
    
    public Domain getDomain() {
        return domain;
    }
    
    abstract public void resetIterators(int iteration);
}
