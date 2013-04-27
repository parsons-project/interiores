package interiores.business.models.backtracking;

import interiores.business.models.backtracking.FurnitureVariable;
import interiores.shared.backtracking.Value;
import interiores.shared.backtracking.VariableSet;

/**
* VariableSet represents a set of variables, each of them with a domain of
* values it can take. A variable might be assigned to a value, or unassigned.
* The variables also have restrictions.
* 
* The union of variables and restrictions define the context of a Constraint
* Satisfaction Problem (CSP).
* A solution to the CSP has been found when each variable has an assigned value
* from its domain such that no restriction is violated.
* 
* Notation
* If the CSP algorithm is in action, the variable which is trying to be assigned
* in the current iteration is denominated the "actual" variable.
* When we try to assign a value to the actual variable, we say we are checking
* that value.
*/
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
        variables = new FurnitureVariable[variableCount];
    }

    
    
    // Implementation from the abstract superclass

    /**
    * Given there is at least one variable without an assigned value, chooses
    * the one that the algorithm should try to allocate next. That is,
    * determines the actual variable.
    */
    protected void setActualVariable() {
        actual = variables[depth];
    }

    
    /**
    * Given that the actual variable has just taken a value, restricts the
    * domain of the remaining unassigned variables to discard invalid values.
    * The function trimDomains is not exhaustive: it does not necessarily
    * eliminate every invalid value from the domains.
    */
    protected void trimDomains() {
        for (int i = depth; i < variableCount; ++i) {
            variables[i].trimDomain(actual);
        }
    }

    
    /**
    * Reverse operation of trimDomains(). Given this function is called after
    * trimDomains() and that "value" is the value that the actual variable had
    * assigned when trimDomains() was called, the domains are restored to what
    * they were before trimDomains().
    * @param value the value that was assigned to the actual variable.
    */
    protected void undoTrimDomains(Value value) {
        for (int i = depth; i < variableCount; ++i) {
            variables[i].undoTrimDomain(actual, value);
        }
    }

    
    /**
    * Indicates whether all variables have an assigned value or not.
    * @return boolean indicating if all variables have been assigned.
    */
    protected boolean allAssigned() {
        return depth == variableCount;
    }

    
    /**
     * Indicates whether the actual variable has more domain values to be
     * checked. 
     * @return boolean indicating if the actual variable has more values.
     */
    protected boolean actualHasMoreValues() {
        return actual.hasMoreValues();
    }
    
    
    /**
     * Given that the actual variable has not been assigned yet and not all of
     * its domain values have been checked, returns the next domain value to be
     * checked.
     * @return Value from the domain of the actual variable to be checked.
     */
    protected Value getNextActualDomainValue() {
        return actual.getNextDomainValue();
    }
    
    
    /**
    * Indicates whether assigning the value 'value' to the actual variable
    * violates any restriction.
    * @param value The Value being checked.
    * @return boolean indicating if the assignment is valid.
    */
    protected boolean canAssignToActual(Value value) {

    }

    
    /**
    * Given that the value "value" can be assigned to the actual variable, it
    * effectuates the assignment.
    * @param value The value to be assigned.
    */
    protected void assignToActual(Value value) {
        actual.assignValue(value);
    }

    
    /**
    * Given that the actual variable has an assigned value, it is left
    * unassigned.
    * @param value The value to be assigned.
    */
    protected void undoAssignToActual() {
        actual.undoAssignValue();
    }
    
    
    /**
    * Eliminates values from the domains of the variables if they violate any
    * restriction by themselves.
    */
    protected void preliminarTrimDomains() {
        
    }
    
}
