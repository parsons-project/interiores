package interiores.business.models.backtracking;

import interiores.business.models.FurnitureType;
import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.business.models.WantedFixed;
import interiores.business.models.WantedFurniture;
import interiores.business.models.WishList;
import interiores.business.models.constraints.furniture.PreliminarTrimmer;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.business.models.constraints.furniture.BinaryConstraint;
import interiores.business.models.constraints.room.GlobalInexhaustiveConstraint;
import interiores.core.Debug;
import interiores.core.business.BusinessException;
import interiores.shared.backtracking.Value;
import interiores.shared.backtracking.VariableSet;
import interiores.utils.BinaryConstraintAssociation;
import interiores.utils.Dimension;
import java.awt.Point;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class FurnitureVariableSet
	extends VariableSet
{	

    /**
     * Represents the number of variables.
     */
    private int variableCount;
    private int constantCount;
    
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
    
    /**
     * This list contains the variables which do not have an assigned value yet.
     */
    List<FurnitureVariable> unassignedVariables;
    
    /**
     * This list contains the variables which have an assigned value.
     */
    List<FurnitureVariable> assignedVariables;
    
    /**
     * This is the variable that the algorithm is trying to assign in the
     * current iteration.
     * It is not in either of the previous lists.
     */
    protected FurnitureVariable actual;
    
    private FurnitureConstant[] constants;
    

    
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
    private VariableConstraintSet binaryConstraints;
   
    /**
     * Indicates restrictions amongst all variables.
     */
    Map<String, GlobalInexhaustiveConstraint> globalConstraints;
    
    private List<PreliminarTrimmer> preliminarTrimmers;
    
    /**
     * constants to calibrate the selection of the next actual variable.
     * The importance of each factor is proportional to the value of the
     * constant.
     */
    private static final int DOMAIN_SIZE_FACTOR = 30;
    private static final int SMALLEST_MODEL_FACTOR = 5;
    private static final int BINARY_CONSTRAINTS_FACTOR = 12;
    
    /**
     * Default Constructor.
     */
    public FurnitureVariableSet(WishList wishList, NamedCatalog<FurnitureType> furnitureCatalog)
            throws BusinessException
    {
        Dimension roomSize = wishList.getRoom().getDimension();
        roomArea = new OrientedRectangle(new Point(0, 0), roomSize, Orientation.S);
        
        variableCount = wishList.getUnfixedSize();
        constantCount = wishList.getFixedSize();
        
        unassignedVariables = new ArrayList();
        assignedVariables = new ArrayList();
        constants = new FurnitureConstant[constantCount];
        
        preliminarTrimmers = new ArrayList();
        
        addConstants(wishList);
        addVariables(wishList, furnitureCatalog, roomSize);
        addBinaryConstraints(wishList);
        
        allAssigned = false;
        actual = null;
    }
    
    private void addConstants(WishList wishList) {
        int i = 0;
        for(WantedFixed wantedFixed : wishList.getWantedFixed()) {
            String constantName = wantedFixed.getName();
            FurnitureValue value = new FurnitureValue(wantedFixed.getPosition(), wantedFixed.getModel(),
                    wantedFixed.getOrientation());
            
            constants[i] = new FurnitureConstant(constantName, value);
            i++;
        }
    }
    
    private void addVariables(WishList wishList, NamedCatalog<FurnitureType> furnitureCatalog,
            Dimension roomSize) {   
           
        for(WantedFurniture wantedFurniture : wishList.getWantedFurniture()) {
            String variableName = wantedFurniture.getName();
            FurnitureType furnitureType = furnitureCatalog.get(wantedFurniture.getTypeName());
            
            unassignedVariables.add(new FurnitureVariable(variableName,
                    furnitureType.getFurnitureModels(), roomSize,
                    wantedFurniture.getUnaryConstraints(), variableCount));
        }
    }
    
    private void addBinaryConstraints(WishList wishList) {
        binaryConstraints = new VariableConstraintSet();

        for(BinaryConstraintAssociation bca : wishList.getBinaryConstraints()) {
            Debug.println("Adding Binary constraint " + bca.toString());
            Debug.println("Element1 is " + getVariable(bca.furniture1).getID());
            Debug.println("Element2 is " + getVariable(bca.furniture2).getID());
            Debug.println("Constraint is " + bca.constraint.toString());

            binaryConstraints.addConstraint(getVariable(bca.furniture1),
                    getVariable(bca.furniture2), bca.constraint);
        }
    }
    
    /**
     * Selects a variable from variables[depth..variableCount-1] and sets it
     * as actual variable.
     * In order for the actual variable to be set it has to be moved to the
     * position depth of variables.
     * The variable is chosen according to 3 factors:
     * 1) The domain size: the smallest the better
     * 2) The binary constraints with variables that have not been set: the more
     * the better
     * 3) The size of the smallest model: the more the better
     * 
     * Each factor has a weight defined by a global constant, so that it can
     * be adjusted.
     * 
     * The iterators of the actual variable are reset.
     */
    //note: trivial implementation. To be optimized.
    
    
    @Override
    protected void setActualVariable() {
        
        if (unassignedVariables.size() == 0)
            allAssigned = true;
        else {
            int maxDomainSize = -1;
            int maxBinaryConstraints = -1;
            int maxSmallestModelArea = -1;
            int[] domainSize = new int[unassignedVariables.size()];
            int[] binaryConstraintsLoad = new int[unassignedVariables.size()];
            int[] smallestModelSize = new int[unassignedVariables.size()];

            int i = 0;
            //get value of each factor for each variable and compute maximums
            for (FurnitureVariable variable : unassignedVariables) {
                domainSize[i] = variable.domainSize();
                if (domainSize[i] > maxDomainSize) maxDomainSize = domainSize[i];

                // the weight of all binary constraints between this variable and
                // other variables which have not been assigned yet.
                binaryConstraintsLoad[i] = 0;
                for (BinaryConstraint bc : binaryConstraints.getConstraints(variable)) {
                    if (bc.getOtherVariable(variable).isAssigned())
                        binaryConstraintsLoad[i] += bc.getWeight(roomArea);
                }
                
                if (binaryConstraintsLoad[i] > maxBinaryConstraints)
                    maxBinaryConstraints = binaryConstraintsLoad[i];

                smallestModelSize[i] = variable.smallestModelSize();
                if (smallestModelSize[i] > maxSmallestModelArea)
                    maxSmallestModelArea = smallestModelSize[i];

            }

            //calculate the weight of each factor for each variable and the final
            //weight of each variable, and compute the index of the variable with
            //the lowest overall weight
            int minimumWeight = -1;
            int minimumWeightVariableIndex = -1;

            for (i = 0; i < unassignedVariables.size(); ++i) {

                int domainSizeWeight = (domainSize[i] * DOMAIN_SIZE_FACTOR) / maxDomainSize;
                int binaryConstraintsWeight = maxBinaryConstraints -
                        (binaryConstraintsLoad[i] * BINARY_CONSTRAINTS_FACTOR) / maxBinaryConstraints;
                int smallestModelSizeWeight =
                        (smallestModelSize[i] * SMALLEST_MODEL_FACTOR) / maxSmallestModelArea;

                int variableWeight = domainSizeWeight + binaryConstraintsWeight +
                        smallestModelSizeWeight;

                if (minimumWeight == -1 || variableWeight < minimumWeight) {
                    minimumWeight = variableWeight;
                    minimumWeightVariableIndex = i;
                }

            }
            
            //set actual variable
            actual = unassignedVariables.get(minimumWeightVariableIndex);
            
            //remove it from the set of unassigned variables
            unassignedVariables.remove(minimumWeightVariableIndex);
            
            //reset iterators
            actual.resetIterators(depth);
        }
    }
 
    
    @Override
    protected void trimDomains() {
        for (FurnitureVariable variable : unassignedVariables) {
            variable.trimDomain(actual, depth);
        }
    }

    
    @Override
    protected void undoTrimDomains(Value value) {
        for (FurnitureVariable variable : unassignedVariables) {
            variable.undoTrimDomain(actual, value, depth);
        }
    }

    
    @Override
    protected boolean allAssigned() {
        if (unassignedVariables.isEmpty() && actual.isAssigned()) {
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
        // Check constant constraints!
        // @TODO Transform to preliminar trims?
        for(FurnitureConstant constant : constants) {
            if(! binaryConstraints.isSatisfied(actual, constant))
                return false;
        }
        
        FurnitureValue actual_fv = (FurnitureValue) value;
        // A little explanation: fv.getArea() gets the ACTIVE area of actual_fv
        // while fv.getWholeArea() gets the PASSIVE + ACTIVE area of actual_fv
        
        if (! roomArea.contains(actual_fv.getWholeArea())) return false;

        actual.assignValue(value);
        for (int i = 0; i < depth; ++i) {
            FurnitureValue other_fv = variables[i].getAssignedValue();
            
            if (!binaryConstraints.isSatisfied(actual, variables[i])
                || actual_fv.getArea().intersects(other_fv.getWholeArea())
                || actual_fv.getWholeArea().intersects(other_fv.getArea()) )
            {
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
        //assignedVariables.add(actual);
    }

    
    @Override
    protected void undoAssignToActual() {
        if (depth >= 0) {
            actual = variables[depth];
        }
        actual.undoAssignValue();
    }
    
    
    public void addPreliminarTrimmer(PreliminarTrimmer preliminarTrimmer) {
        preliminarTrimmers.add(preliminarTrimmer);
    }
    
    //note: trivial implementation. To be optimized.
    @Override
    protected void preliminarTrimDomains() {
        for(PreliminarTrimmer preliminarTrimmer : preliminarTrimmers)
            preliminarTrimmer.preliminarTrim(constants, variables);
        
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
    
    
    private InterioresVariable getVariable(String name) {
        for (FurnitureVariable variable : unassignedVariables)
            if (variable.getID().equals(name)) return variable;
        for (FurnitureVariable variable : assignedVariables)
            if (variable.getID().equals(name)) return variable;
        if (actual.getID().equals(name)) return actual;
            
        
        throw new BusinessException(name + " variable not found.");
    }
   
    
    public Map<String, FurnitureValue> getValues() {
        Map<String, FurnitureValue> values = new HashMap();
        
        for(FurnitureConstant constant : constants)
            values.put(constant.getID(), constant.getAssignedValue());
        
        for(FurnitureVariable variable : unassignedVariables)
            values.put(variable.getID(), variable.getAssignedValue());
     
        for(FurnitureVariable variable : assignedVariables)
            values.put(variable.getID(), variable.getAssignedValue());
        
        return values;
    }
 
    
//    @Override
//    public String toString() {
//        StringBuilder result = new StringBuilder();
//        String NEW_LINE = System.getProperty("line.separator");
//
//        result.append(this.getClass().getName() + ":" + NEW_LINE);
//        result.append("Iteration: " + depth + NEW_LINE);
//        result.append("Number of variables: " + variableCount + NEW_LINE);
//        
//        result.append("Variables: " + NEW_LINE);
//        for (FurnitureVariable variable : assignedVariables) {
//            result.append(variable.getID() + ": ");
//            result.append(variable.getAssignedValue().toString() + NEW_LINE);
//        }
//        result.append("Actual variable:" + NEW_LINE);
//        if (actual == null) result.append("none" + NEW_LINE);
//        else result.append(actual.toString());
//        result.append("Are all variables assigned? " + NEW_LINE);
//        if (allAssigned) result.append("All assigned: Yes" + NEW_LINE);
//        else result.append("No" + NEW_LINE);
//        result.append("Binary restrictions:" + NEW_LINE);
//        result.append(binaryConstraints.toString());
//
//        return result.toString();
//    }

}
