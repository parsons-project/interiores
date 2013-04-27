
import interiores.business.models.backtracking.AATree;
import interiores.core.business.Model;
import java.util.List;

/**
* FurnitureVariable takes the role of variable in the context of the Constraint
* Satisfaction Problem (CSP) for the room design. A variable in this context
* involves a domain of values and, optionally, an assigned value.
*/
public class FurnitureVariable
	implements Variable
{
    public Value assignedValue;

    /**
    * This vector of lists contains all models available for this variable.
    * The models belong to the furniture type associated to the variable.
    * 
    * Moreover, it gives information about what models have been discarded and,
    * in such cases, in which iteration of the algorithm they were discarded:
    * At any given iteration i (depth = i) of the algorithm, all models which
    * have not been discarded are in the list in the i position of the vector.
    * Models which have been discarded are in the list in the position
    * correspondent to the iteration in which they were discarded.
    * Lists beyond the position i are empty.
    * 
    * The vector size never changes and is equal to the amount of iterations of
    * the algorithm.
    */
    List<Model>[] domainModels;

    /**
    * This vector of AA-Trees contains all positions available for this variable.
    * 
    * Moreover, it gives information about what positions have been discarded and,
    * in such cases, in which iteration of the algorithm they were discarded:
    * At any given iteration i (depth = i) of the algorithm, all positions which
    * have not been discarded are in the tree in the i position of the vector.
    * Positions which have been discarded are in the tree in the position
    * correspondent to the iteration in which they were discarded.
    * Trees beyond the position i are empty.
    * 
    * The vector size never changes and is equal to the amount of iterations of
    * the algorithm.
    */
    AATree[] domainPositions;
    
    /**
    * Orientation: not included so far
    * 
    */


    
    /**
    * Constructor. I don't know what parameters this has...
    */
    public FurnitureVariable(int variableCount) {
        assignedValue = null;
        domainModels = new List<Model>[variableCount];
        domainPositions = new AATree[variableCount];
    }


    // Implementation from the abstract superclass



    /**
    * Given that the variable is the actual variable that the algorithm is
    * trying to assign and not all of its domain values have been checked,
    * returns the next domain value to be checked.
    * @return Value the next domain value
    */
    public Value getNextDomainValue();

    
    /**
    * Given that the variable is the actual variable that the algorithm is
    * trying to assign, returns whether the variable has domain values which
    * have not been checked yet.
    * @return boolean indicating if there are more domain values
    */
    public boolean hasMoreValues();

    
    /**
    * Assigns the value 'value' to the variable. Doing this changes the state of
    * the variable to assigned.
    */
    public void assignValue(Value value);

    
    /**
    * Changes the state of the variable to unassigned.
    */
    public void undoAssignValue();

    
    /**
    * Given that the variable 'variable' has been recently assigned to
    * a valid value, restricts the domain of the current variable, discarding
    * the values that won't conform to this assignation.
    * This function is not exhaustive: it does not necessarily
    * eliminate every invalid value.
    */
    public void trimDomain(Variable variable);

    
    /**
    * Reverse operation of trimDomain(). Given that this function is called with
    * the parameter "variable" after trimDomain(variable), the domains are
    * restored to what they were before trimDomain().
    * @param variable the variable which was the parameter of trimDomain()
    * @param value the value that was assigned to the variable "variable".
    */
    public void undoTrimDomain(Variable variable, Value value);

    
    /**
    * Returns whether the variable is assigned.
    * @return boolean indicating if the variable is assigned.
    */
    public boolean isAssigned ();

    
    /**
    * Returns the assigned value óf the variable, given it has one.
    * @return Value the assigned value of the variable
    */
    public Value getAssignedValue ();
}
	
	




// DISCARDED MATERIAL

/**
    * This vector contains, for each iteration of the algorithm,
    * a map indicating for all discrete position of the room if they are
    * banned or allowed for this specific variable.
    */
    BitMap[] PositionMap;

	
	