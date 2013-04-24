/**
* VariableSet represents a set of variables , each of
* them with a domain of values it can take . A variable
* might be assigned to a value , or unassigned .
*/

public enum Cell {
		ACTIVE, PASSIVE, EMPTY, SUPPORT, WALKPATH;
}

public abstract class FurnitureVariableSet
	extends VariableSet
{

	
	/**
	* This map contains, for each iteration of the algorithm,
	* the current state of all discrete positions of the room.
	*/
	private Cell[][][] map;
	
	
	public FurnitureVariableSet(...) {
	
	}
	


/**
* Given there is at least one variable without an
* assigned value , returns the one which , seemingly ,
* the algorithm should try to allocate next in order
* to minimize the search tree 's size .
*/
public abstract Variable next ( void );
/**
* Given that the variable 'variable ' has just taken a value ,
* restricts the domain of the remaining unassigned variables
* to discard invalid values . The function trimDomains is
* not necessarily exhaustive .
* @param variable Variable which constrains the other variables .
*/
public abstract void trimDomains ( Variable variable );
/**
* Rolls back the changes made to the domains of unassigned
* variables by the trimDomains () function , where the
* 'variable ' parameters must coincide and 'value ' must be the
* value which that variable had assigned upon the
* trimDomains () call .
* @param variable Variable which no longer constrains other variables
* @param value Value assigned to the variable
*/
public abstract void undoTrimDomains ( Variable variable , Value value );
/**
* Indicates whether all variables have an assigned
* value or not.
* @return boolean indicating if all variable have been assigned
*/
public abstract boolean allAssigned ( void );
/**
* Indicates whether assigning the value 'value ' to the
* variable 'variable ' violates any restriction .
* @param variable Variable the current variable being checked .
* @param value Value to be assigned to the variable .
* @return boolean indicating if it is possible to
* assign value to variable
*/
public abstract boolean canPlace ( Variable variable , Value value );
/**
* Assigns the value 'value ' to the variable 'variable '.
*/
public abstract void place ( Variable variable , Value value );
/**
* Leaves the variable 'variable ' as unassigned .
*/
public abstract void remove ( Variable variable );
}