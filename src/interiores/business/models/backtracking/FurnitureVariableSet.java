package interiores.business.models.backtracking;

import interiores.business.models.FurnitureType;
import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.business.models.WantedFixed;
import interiores.business.models.WantedFurniture;
import interiores.business.models.WishList;
import interiores.business.models.constraints.furniture.PreliminarTrimmer;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.business.models.constraints.Constraint;
import interiores.business.models.constraints.furniture.BinaryConstraint;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.business.models.constraints.room.RoomInexhaustiveTrimmer;
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
    
    List<FurnitureConstant> constants;
    
    /**
     * Indicates whether all variables have an assigned value.
     */
    protected boolean allAssigned;

    /**
     * Contains the room rectangle
     */
    private OrientedRectangle roomArea;

    /**
     * Indicates restrictions amongst all variables.
     */
   List<GlobalConstraint> globalConstraints;
    
   /**
    * Holds informatinon about the relationship between constraints of
    * different variables.
    * The first string identifies the ID of the variable that, when assigned,
    * the domain of the variable identified by the second string, will be
    * trimmed proportionally to the dependence stored in this matrix.
    * 
    * Detailed explanation in the header of Constraint class.
    */
   Map<Entry<String, String>, Integer> matrixOfDependence;
    
    /**
     * constants to calibrate the selection of the next actual variable.
     * The importance of each factor is proportional to the value of the
     * constant.
     */
    private static final int DOMAIN_SIZE_FACTOR = 30;
    private static final int SMALLEST_MODEL_FACTOR = 5;
    private static final int BINARY_CONSTRAINTS_FACTOR = 12;
    
    /**
     * Constructor.
     * This constructor does all the initilization work:
     * 
     * roomArea is initialized with the room rectangle.
     * 
     * variableCount is initialized with the number of variables.
     * 
     * allAssigned is initialized false.
     * 
     * assignedVariables is initialized empty.
     * 
     * actual is initialized null.
     * 
     * unassignedVariables is initialized with all variables.
     * Each variable is initialized with:
     * - all the models available for its furniture type.
     * - all the orientations
     * - all the positions of the room (roomArea)
     * - the list of unary constraints it is associated with.
     * 
     * constants is initialized with all FurnitureConstants.
     * 
     * globalConstraints is initialized with all global constraints.
     * The TrimUnfitModelsPseudoContraint is placed last in the list.
     * 
     * The matrix of dependence is built
     */
    public FurnitureVariableSet(WishList wishList, NamedCatalog<FurnitureType> furnitureCatalog)
            throws BusinessException {

        Dimension roomSize = wishList.getRoom().getDimension();
        roomArea = new OrientedRectangle(new Point(0, 0), roomSize, Orientation.S);
        
        variableCount = wishList.getUnfixedSize();
        
        allAssigned = false;
   
        assignedVariables = new ArrayList<FurnitureVariable>();
        
        actual = null;
        
        unassignedVariables = new ArrayList<FurnitureVariable>();
        addVariables(wishList, furnitureCatalog, roomSize);
        
        constants = new ArrayList<FurnitureConstant>();
        addConstants(wishList);
        
        globalConstraints = new ArrayList<GlobalConstraint>();
        addGlobalConstraints();
        
        buildMatrixOfDependence();

    }
    
    /**
     * Invoked in the constructor to initialize constants.
     * @param wishList 
     */
    private void addConstants(WishList wishList) {
        for(WantedFixed wantedFixed : wishList.getWantedFixed()) {
            String constantName = wantedFixed.getName();
            FurnitureValue value = new FurnitureValue(wantedFixed.getPosition(), wantedFixed.getModel(),
                    wantedFixed.getOrientation());
            
            constants.add(new FurnitureConstant(constantName, value));
        }
    }
    
    /**
     * Invoked in the constructor to initialize variables.
     * @param wishList
     * @param furnitureCatalog
     * @param roomSize 
     */
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
    
    /**
     * Invoked in the constructor to initialize global constraints.
     */
    private void addGlobalConstraints() {
        throw new UnsupportedOperationException("Not supported yet.");
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
    @Override
    protected void setActualVariable() {
        
        if (unassignedVariables.isEmtpy())
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
 
    // Algorithm to build the matrix of dependence:
    // Initialize mod to all 0.
    // For each binary constraint bc:
    //      //assume v is the variable associated with bc
    //      //and w is the otherVariable of bc (bc.getOtherVariable())
    //      mod[w][v] = bc.getWeight()
    //      //this means that if w is assigned a value, the domain of v will be
    //      //restricted proportionally to the weight of bc.
    private void buildMatrixOfDependence() {

        for (FurnitureVariable v : unassignedVariables) {
            for (Constraint constraint : v.furnitureConstraints) {
                if (constraint instanceof BinaryConstraint) {
                    BinaryConstraint bc = (BinaryConstraint) constraint;
                    
                    InterioresVariable otherVariable = bc.getOtherVariable();
                    if (otherVariable instanceof FurnitureVariable) {
                        FurnitureVariable w = (FurnitureVariable) otherVariable;

                        Entry e = new SimpleEntry(w.getID(), v.getID());
                        matrixOfDependence.put(e, bc.getWeight());
                    }
                }
            }
        }
    }
    
    private int getDependence(FurnitureVariable variable1, FurnitureVariable variable2) {
        Entry e = new SimpleEntry(variable1.getID(), variable2.getID());
        if (! matrixOfDependence.containsKey(e))
            return 0;
        else return matrixOfDependence.get(e);
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
