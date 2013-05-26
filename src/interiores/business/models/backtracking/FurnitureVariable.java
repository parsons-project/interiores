package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Orientation;
import interiores.business.models.backtracking.Area.Area;
import interiores.business.models.constraints.Constraint;
import interiores.business.models.constraints.furniture.InexhaustiveTrimmer;
import interiores.business.models.constraints.furniture.PreliminarTrimmer;
import interiores.business.models.constraints.furniture.UnaryConstraint;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.business.models.constraints.room.RoomInexhaustiveTrimmer;
import interiores.business.models.constraints.room.RoomPreliminarTrimmer;
import interiores.shared.backtracking.Value;
import interiores.shared.backtracking.Variable;
import interiores.utils.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FurnitureVariable
	extends InterioresVariable
{
    @XmlElementWrapper
    protected Map<Class, Constraint> furnitureConstraints;
    
    @XmlTransient
    protected Domain domain;
    
   /**
    * Represents the iteration of the algorithm.
    */
    @XmlTransient
    public int iteration = 0;  
    
    /**
     * Value of the cheapest model
     */
    @XmlTransient
    private float minPrice = -1;
    private int maxWidth = -1;
    private int maxDepth = -1;
    
    /**
     * Default constructor.
     * JAXB needs it!
     */
    public FurnitureVariable()
    { }
    
    /**
     * Default Constructor. The resulting variable has as domain the models in
     * "models", every position in room and all orientations.
     * The set of restrictions is "unaryConstraints". Its resolution defaults to 5.
     * @pre the iteration of the variableSet is 0
     */
<<<<<<< HEAD
    public FurnitureVariable(String id, List<FurnitureModel> models, Dimension roomSize,
            List<Constraint> furnitureConstraints, int variableCount)
=======
    public FurnitureVariable(String id, String typeName)
>>>>>>> 90c6180a810f51fb59cfdf50f2740b893b472545
    {
        super(id, typeName);
        
<<<<<<< HEAD
        this.furnitureConstraints = furnitureConstraints;
        
        //minPrice not calculated yet
        minPrice = -1;
=======
        furnitureConstraints = new HashMap();
>>>>>>> 90c6180a810f51fb59cfdf50f2740b893b472545
    }
    
    public FurnitureVariable(String id, String typeName, FurnitureValue value) {
        this(id, typeName);
        
        assignValue(value);
    }
    
    @Override
    public boolean isConstant() {
        return false;
    }
    
    public void createDomain(List<FurnitureModel> models, Dimension roomSize, int variableCount) {
        domain = new Domain(models, roomSize, variableCount);
    }
    
    public Collection<Constraint> getConstraints() {
        return furnitureConstraints.values();
    }
    
    /**
     * Resets the iterators so that they will iterate through all of the
     * variables' domains, for the iteration "iteration" of the algorithm.
     */
    public void resetIterators(int iteration) {
        domain.resetIterators(iteration);
    }
    
    //Pre: we have not iterated through all domain values yet.
    @Override
    public Value getNextDomainValue() {
        return domain.getNextDomainValue(iteration);
    }  
    
    //Pre: the 3 iterators point to valid values
    @Override
    public boolean hasMoreValues() {
        return domain.hasMoreValues(iteration);
    }
  
    /**
     * Moves positions, models and orientations which are still valid to the
     * next level.
     * All positions from the HashSet at the position "iteration" which are
     * still valid must be moved to the HashSet in the position "iteration"+1.
     * To do this operation, we move all positions preliminarily, and then move
     * back those that are not valid. We estimate this reduces the amount of
     * HashSet operations.
     * All models from the List at the position "iteration" which are still 
     * valid must be moved to the List in the position "iteration"+1.
     */
    //pre: variable has an assigned value.
    //pre: if trimDomain or undoTrimDomain has already been called once,
    //     "iteration" value must be related to the value of "iteration" of the
    //     previous call (+1 if it was a trimDomain or equal if it was a
    //     undoTrimDomain).
    //     otherwise, it must be 0.
    //
    @Override
    public void trimDomain(Variable variable, int iteration) {
        // 0) update internal iteration
        this.iteration = iteration + 1;
        
        // Prepare next iteration
        // 1) preliminar move of all positions
        forwardIteration();
               
        // 2) send the affected positions back
        FurnitureValue value = (FurnitureValue) variable.getAssignedValue();
        Rectangle invalidRectangle = value.getWholeArea();
        
        domain.trimAndPushInvalidRectangle(invalidRectangle, iteration);        
        
        // 3) move all models
        domain.saveAllModels(iteration);
        
        // 4) move all orientations
        domain.saveAllOrientations(iteration);
    }

    
    /**
     * Merges back values from step "iteration"+1 to "iteration" level.
     * To do this operation, we swap the containers first if the destination
     * level's container has less elements.
     */
    //pre: trimDomain has already been called once.
    //     "iteration" value must be related to the value of "iteration" of the
    //     previous call to trimDomain or undoTrimDomain (equal if it was
    //     trimDomain or -1 if it was undoTrimDomain).
    @Override
    public void undoTrimDomain(Variable variable, Value value, int iteration) {
        domain.undoTrimDomain(iteration);       
    }
    
    public Domain getDomain() {
        return domain;
    }
    
    /**
     * Returns the price of the cheapest model.
     * @return 
     */
    public float getMinPrice() {
        return minPrice;
    }
    
    private void initializeMaxMinFields() {
        minPrice = -1;
        maxWidth = 0;
        maxDepth = 0;
        for (FurnitureModel model : domain.getModels(0)) {
            if (minPrice == -1 || model.getPrice() < minPrice)
                minPrice = model.getPrice();
            if (model.getSize().width > maxWidth)
                maxWidth = model.getSize().width;
            if (model.getSize().depth > maxDepth)
                maxDepth = model.getSize().depth;
        }
        if (minPrice == -1) minPrice = 0;
    }
    
    void trimTooExpensiveModels(float maxPrice) {
        Iterator<FurnitureModel> it = domain.getModels(0).iterator();        
        while (it.hasNext()) {
            FurnitureModel model = it.next();
            if (model.getPrice() > maxPrice) it.remove();
        }
    }
    
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");

        result.append(this.getClass().getName() + ":" + NEW_LINE);
        result.append("Assigned value: ");
        if (isAssigned()) result.append(assignedValue.toString() + NEW_LINE);
        else result.append("none" + NEW_LINE);
        
        //result.append(" Models available" + NEW_LINE);
        //for (FurnitureModel model : domainModels)
        //        result.append(model.getName() + " ");
        
        
//        result.append(" Positions available by iteration" + NEW_LINE);
//        result.append("iteration    positions" + NEW_LINE);
//        for (int i = 0; i < domainPositions.length && domainPositions[i] != null; ++i) {
//            result.append(i + "             [ ");
//            for (Point point : domainPositions[i]) {
//                result.append("(" + point.x + "," + point.y + ") ");
//            }
//             result.append("]" + NEW_LINE);    
//        }        
        
        result.append(" Constraints:" + NEW_LINE);
        for (UnaryConstraint constraint : unaryConstraints) {
            result.append(constraint.toString());
        }

        return result.toString();
    }

    void trimObstructedPositions() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    int domainSize() {
        return domain.domainSize(iteration);
    }

    int smallestModelSize() {
        return domain.smallestModelSize(iteration);
    }

    
    
    //FUNCTIONS TO MODIFY THE DOMAIN
    //forwardIteration(), and consfraint - variable interface
    
    /**
     * Moves all values of the current iteration to the next iteration
     */
    public void forwardIteration() {
        domain.forwardIteration(iteration);
    }
    
   
    //CONSTRAINT - VARIABLE INTERFACE
    
    public void eliminateExceptP(Area validPositions) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
       /**
     * Any value of the domain of the next iteration included in the
     * parameter is trimmed (moved to the previous iteration)
     * @param invalidArea 
     */
    public void trim(Area invalidArea) {
        domain.trim(invalidArea, iteration);
    }
    
    
    /**
     * Any value of the domain of the next iteration not included in the
     * parameter is trimmed (moved to the previous iteration)
     * @param validArea 
     */
    public void trimExceptP(Area validArea) {
        domain.trimExceptP(validArea, iteration);
    }

    public void eliminateExceptM(HashSet<FurnitureModel> validModels) {
        domain.eliminateExceptM(validModels);
    }

    public void eliminateExceptO(HashSet<Orientation> validOrientations) {
        domain.eliminateExceptO(validOrientations);
    }

    public void trimExceptM(HashSet<FurnitureModel> validModels) {
        domain.trimExceptM(validModels, iteration);
    }

    public void trimExceptO(HashSet<Orientation> validOrientations) {
        domain.trimExceptO(validOrientations, iteration);
    }
        
    void triggerPreliminarTrimmers() {
        Iterator<Constraint> it = furnitureConstraints.iterator();
        while(it.hasNext()) {
            Constraint constraint = it.next();
            if (constraint instanceof PreliminarTrimmer) {
                PreliminarTrimmer preliminarTrimmer = (PreliminarTrimmer) constraint;
                preliminarTrimmer.preliminarTrim(this);
            }
            //ditch it if it doesn't implement any other interface
            if (! (constraint instanceof InexhaustiveTrimmer))
                it.remove();
        }
    }

    boolean constraintsSatisfied() {
        for (Constraint constraint : furnitureConstraints)
            if (! ((InexhaustiveTrimmer) constraint).isSatisfied(this))
                return false;
        return true;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getMaxDepth() {
        return maxDepth;
    }
    
    
}