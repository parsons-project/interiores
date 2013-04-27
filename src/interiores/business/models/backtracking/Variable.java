/**
* Variable represents a variable, which has a domain of values it can take, and
* a set of restrictions.
* It might have a value assigned (from its domain) or be unassigned.
*/
public interface Variable
{


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