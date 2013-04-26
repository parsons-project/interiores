/**
* Variable represents a variable , which has a domain of values
* it can take . It might have a value assigned or be unassigned .
*/
public abstract class Variable
{

/**
* Returns the value to be evaluated next by the algorithm ,
* attending to a particular criteria , specific to each
* implementation .
* pre: not all values have been checked yet
*/
public abstract Value getNextDomainValue ();

/**
* Returns whether the variable being avaluated has more possible values.
*/
public abstract boolean hasMoreValues();

/**
* Assigns a particular 'value ' to the variable . Doing this
* changes the state of the variable to 'assigned '.
*/
public abstract void setAssignedValue ( Value value );

/**
* Changes the state of the variable to 'unassigned '.
*/
public abstract void unsetValue ( void );

/**
* Given a 'variable ' that has been recently assigned to
* a valid value , trimDomain () restricts the domain of the
* current variable , discarding those values that won 't
* conform to this assignation . This function is not
* necessarily exhaustive .
*/
public abstract void trimDomain ( Variable variable );

/**
* Restores the changes made to the domain of the current
* variable by the assignment of 'variable ' to 'value ', which
* is no longer valid .
*/
public abstract void undoTrimDomain ( Variable variable , Value value );

/**
* Returns 'true ' if the variable is assigned to a valid
* value , and 'false ' otherwise .
*/
public abstract boolean isAssigned ();

/**
* Returns the assigned value óf the variable, given it has one.
*/
public abstract Value getAssignedValue (); 
}