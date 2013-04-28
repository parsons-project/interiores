package interiores.business.models.backtracking;

import interiores.business.models.backtracking.FurnitureVariable;
import interiores.shared.backtracking.Value;
import interiores.shared.backtracking.VariableSet;

public class FurnitureVariableSet
	extends VariableSet
{	

    private int variableCount;
    
    /**
     * Contains all the variables of the variable set.
     * Moreover, it gives information about what variables have an assigned
     * value and, in such cases, in which iteration of the algorithm they
     * were assigned:
     * At any given iteration i (depth = i) of the algorithm, the first i
     * elements of the vector have assigned values, and they were assigned in
     * the iteration correspondent to their position in the vector.
     * Variables beyond the first i positions have no assigned value yet.
     * 
     * The vector size never changes and is equal to the amount of variables
     * (which is also the number of iterations of the algorithm), but their
     * elements might be reallocated.
     */
    private FurnitureVariable[] variables;
    
    /**
     * This is the variable that the algorithm is trying to assign in the
     * current iteration.
     */
    private FurnitureVariable actual;
    
    /**
     * Indicates whether all variables have an assigned value.
     */
    boolean allAssigned;
    
    
    public enum Cell {
		ACTIVE,PASSIVE,EMPTY,SUPPORT,WALKPATH;
    }
    
    /**
    * This map contains, for each iteration of the algorithm,
    * the current state of all discrete positions of the room.
    * This attribute might be necessary to check that every place in the room is
    * accessible.
    */
    private Cell[][][] map;


       
    /**
     * Constructor. I don't know what parameters this has...
     */
    public FurnitureVariableSet(...) {
        variableCount = ...;
        allAssigned = false;
        variables = new FurnitureVariable[variableCount];
    }

    
    
    // Implementation from the abstract superclass

    @Override
    protected void setActualVariable() {
        actual = variables[depth];
    }

    
   @Override
    protected void trimDomains() {
        for (int i = depth; i < variableCount; ++i) {
            variables[i].trimDomain(actual);
        }
    }

    
    @Override
    protected void undoTrimDomains(Value value) {
        for (int i = depth; i < variableCount; ++i) {
            variables[i].undoTrimDomain(actual, value);
        }
    }

    
    @Override
    protected boolean allAssigned() {
        if (depth == (variableCount - 1) && actual.isAssigned()) {
            allAssigned = true;
        }
        return allAssigned;
    }

    
    @Override
    protected boolean actualHasMoreValues() {
        return actual.hasMoreValues();
    }
    
    
    @Override
    protected Value getNextActualDomainValue() {
        return actual.getNextDomainValue();
    }
    
    
    @Override
    protected boolean canAssignToActual(Value value) {

    }

    
    @Override
    protected void assignToActual(Value value) {
        actual.assignValue(value);
    }

    
    @Override
    protected void undoAssignToActual() {
        actual.undoAssignValue();
    }
    
    
    @Override
    protected void preliminarTrimDomains() {
        
    }
    
}
