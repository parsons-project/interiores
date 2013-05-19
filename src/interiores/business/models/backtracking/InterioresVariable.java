package interiores.business.models.backtracking;

import interiores.shared.backtracking.Value;
import interiores.shared.backtracking.Variable;

/**
 *
 * @author hector
 */
abstract public class InterioresVariable implements Variable
{
    private String identifier;
    
    /**
    * Represents the value taken by the variable, in case it is assigned.
    * Only valid when isAssigned is true.
    */
    public FurnitureValue assignedValue;
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
    public FurnitureValue getAssignedValue() {
        return assignedValue;
    }
       
    @Override
    public void assignValue(Value value) {
        isAssigned = true;
        assignedValue = (FurnitureValue) value;
    }

    
    @Override
    public void undoAssignValue() {
        isAssigned = false;
        assignedValue = null;        
    }
}
