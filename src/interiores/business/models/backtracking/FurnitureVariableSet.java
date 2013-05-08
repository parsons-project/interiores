package interiores.business.models.backtracking;

import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.business.models.Room;
import interiores.business.models.WishList;
import interiores.business.models.constraints.BinaryConstraintSet;
import interiores.business.models.constraints.GlobalConstraint;
import interiores.core.Debug;
import interiores.shared.backtracking.Value;
import interiores.shared.backtracking.VariableSet;
import interiores.utils.BinaryConstraintAssociation;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class FurnitureVariableSet
	extends VariableSet
{	

    /**
     * Represents the number of variables.
     */
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
    protected FurnitureVariable[] variables;
    
    /**
     * This is the variable that the algorithm is trying to assign in the
     * current iteration.
     */
    protected FurnitureVariable actual;
    
    /**
     * Indicates whether all variables have an assigned value.
     */
    protected boolean allAssigned;

    /**
     * Contains the room rectangle
     */
    private OrientedRectangle roomArea;
    
    /**
     * Indicates restrictions amongst two variables.
     */
    private BinaryConstraintSet binaryConstraints;
   
    /**
     * Indicates restrictions amongst all variables.
     */
    Map<String, GlobalConstraint> globalConstraints;
            
    /**
     * Default Constructor.
     */
    public FurnitureVariableSet(Room room, WishList wishList)
    {
        roomArea = new OrientedRectangle(new Point(0, 0), room.getDimension(), Orientation.S);
        
        variableCount = wishList.getSize();
        variables = new FurnitureVariable[variableCount];
        
        int i = 0;
        for(String variableName : wishList.getFurnitureNames()) {
            variables[i] = new FurnitureVariable(variableName, wishList.getFurnitureModels(variableName),
                    room.getDimension(), wishList.getUnaryConstraints(variableName), variableCount);
            ++i;
        }

       binaryConstraints = new BinaryConstraintSet();
       
       for(BinaryConstraintAssociation bca : wishList.getBinaryConstraints()) {
           Debug.println("Adding Binary constraint " + bca.toString());
           Debug.println("Furniture1 is " + getVariable(bca.furniture1).getID());
           Debug.println("Furniture2 is " + getVariable(bca.furniture2).getID());
           Debug.println("Constraint is " + bca.constraint.toString());
           
           binaryConstraints.addConstraint(getVariable(bca.furniture1),
                   getVariable(bca.furniture2), bca.constraint);
       }
        
       allAssigned = false;
       actual = null;
    }
   

    /**
     * Selects a variable from variables[depth..variableCount-1] and sets it
     * as actual variable.
     * The iterators of the actual variable are reset.
     */
    //note: trivial implementation. To be optimized.
    @Override
    protected void setActualVariable() {
        if (variables.length != 0) {
            actual = variables[depth];
            actual.resetIterators(depth);
        }
        else {
            allAssigned = true;
        }
    }
 
    
    @Override
    protected void trimDomains() {
        for (int i = depth + 1; i < variableCount; ++i) {
            variables[i].trimDomain(actual, depth);
        }
    }

    
    @Override
    protected void undoTrimDomains(Value value) {
        for (int i = depth + 1; i < variableCount; ++i) {
            variables[i].undoTrimDomain(actual, value, depth);
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
    
    
    //note: preliminar implementation. Final implementation should take more
    //things into consideration (e.g., not blocking paths)
    @Override
    protected boolean canAssignToActual(Value value) {
        
        OrientedRectangle actualArea = ((FurnitureValue) value).getArea();
        
        if (! roomArea.contains(actualArea)) return false;

        actual.assignValue(value);
        for (int i = 0; i < depth; ++i) {
            OrientedRectangle otherArea =
                ((FurnitureValue) variables[i].getAssignedValue()).getArea();
            
            if (!binaryConstraints.isSatisfied(actual, variables[i]) ||
                actualArea.intersects(otherArea)) {
                actual.undoAssignValue();
                return false;
            }

        }
        actual.undoAssignValue();
        return true;
    }

    
    @Override
    protected void assignToActual(Value value) {        
        actual.assignValue(value);
    }

    
    @Override
    protected void undoAssignToActual() {
        if (depth >= 0) {
            actual = variables[depth];
        }
        actual.undoAssignValue();
    }
    
    
    //note: trivial implementation. To be optimized.
    @Override
    protected void preliminarTrimDomains() {
        
        //1) remove values which do not fit some unary constraint
        for (int i = 0; i < variableCount; ++i)
            variables[i].applyUnaryConstraints();
        
        //2) remove pieces of furniture such that there is another piece
        // smaller and cheaper
        for (int i = 0; i < variableCount; ++i)
            variables[i].trimUnfitModels();
        
        // @TODO Refactorize
        //3) remove furniture too expensive
        /*float minBudget = 0;
        for (int i = 0; i < variableCount; ++i)
            minBudget += variables[i].getMinPrice();
       
        for (int i = 0; i < variableCount; ++i) {
            //if a model from this variable is more expensive than maxPrice,
            //there is no possible assignmentment to variables such that
            //variables[i] has assigned this model and the maxBudget is not exceeded 
            float maxPrice = maxBudget - ( minBudget - variables[i].getMinPrice());
            variables[i].trimTooExpensiveModels(maxPrice);
        }
        
        //4) remove positions such that no model fit there due to walls and
        //topology elements
        for (int i = 0; i < variableCount; ++i)
            variables[i].trimObstructedPositions();*/
        
    }
    
    
    private FurnitureVariable getVariable(String name) {
        for (int i = 0; i < variableCount; i++)
            if (variables[i].getID().equals(name)) return variables[i];
        return null;
    }
   
    
    public Map<String, FurnitureValue> getValues() {
        Map<String, FurnitureValue> values = new HashMap();
        
        for(int i = 0; i < variables.length; ++i)
            values.put(variables[i].getID(), (FurnitureValue) variables[i].getAssignedValue());
        
        return values;
    }
 
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");

        result.append(this.getClass().getName() + ":" + NEW_LINE);
        result.append("Iteration: " + depth + NEW_LINE);
        result.append("Number of variables: " + variableCount + NEW_LINE);
        
        result.append("Variables: " + NEW_LINE);
        for (int i = depth; i < variableCount; ++i) {
            result.append(variables[i].getID() + ": ");
            result.append(variables[i].getAssignedValue().toString() + NEW_LINE);
        }
//        result.append("Actual variable:" + NEW_LINE);
//        if (actual == null) result.append("none" + NEW_LINE);
//        else result.append(actual.toString());
//        result.append("Are all variables assigned? " + NEW_LINE);
        if (allAssigned) result.append("All assigned: Yes" + NEW_LINE);
        else result.append("No" + NEW_LINE);
        result.append("Binary restrictions:" + NEW_LINE);
        result.append(binaryConstraints.toString());

        return result.toString();
    }
}
