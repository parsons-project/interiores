package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.business.models.Room;
import interiores.business.models.constraints.ModelConstraint;
import interiores.shared.backtracking.Value;
import interiores.shared.backtracking.Variable;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import interiores.core.business.Model;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

      
public class FurnitureVariable
	implements Variable
{
 

    /**
    * This vector of lists contains all models available for this variable.
    * The models belong to the furniture type associated to the variable.
    * 
    * Moreover, it gives information about what models have been discarded and,
    * in such cases, in which iteration of the algorithm they were discarded:
    * At any given iteration i (depth = i) of the algorithm, all models which
    * have not been discarded are in the list at the i position of the vector.
    * Models which have been discarded are in the list at the position
    * correspondent to the iteration in which they were discarded.
    * Lists beyond the position i are empty.
    * 
    * The vector size never changes and is equal to the amount of iterations of
    * the algorithm.
    */
    public List<FurnitureModel>[] domainModels;

    /**
    * This vector of hash sets contains all positions available for this variable.
    * 
    * Moreover, it gives information about what positions have been discarded and,
    * in such cases, in which iteration of the algorithm they were discarded:
    * At any given iteration i (depth = i) of the algorithm, all positions which
    * have not been discarded are in the set at the i position of the vector.
    * Positions which have been discarded are in the set at the position
    * correspondent to the iteration in which they were discarded.
    * Sets beyond the position i are empty.
    * 
    * The vector size never changes and is equal to the amount of iterations of
    * the algorithm.
    */
    public HashSet<Point>[] domainPositions;
    
    /**
     * This list contains all four possible orientations.
     */
    List<Orientation> orientations;
    
    /**
     * This list contains the constraints regarding the model of the variable.
     */
    public List<ModelConstraint> constraints;


    /**
    * Represents the value taken by the variable, in case it is assigned.
    * Only valid when isAssigned is true.
    */
    public Value assignedValue;
    boolean isAssigned;

    
    /**
    * Represents the iteration of the algorithm.
    */
    public int iteration;
    
    // The following variables are used to iterate ovre the domain.
    //Iteration is done in this order: 1) Position, 2) Orientation, 3) Models
    private Iterator positionIterator;
    private Iterator orientationIterator;
    private Iterator modelIterator;
    
    private Point currentPosition;
    private Orientation currentOrientation;
    private FurnitureModel currentModel;
    
 
    
    
    /**
    * Default Constructor. The resulting variable has as domian the models in
    * "models", every position in room and all orientations.
    * The set of restrictions is "constraints".
    */
    public FurnitureVariable(List<FurnitureModel> models, Room room,
            List<ModelConstraint> constraints, int variableCount) {
        
        isAssigned = false;
        iteration = 0;

        domainModels = new List<FurnitureModel>[variableCount];
        domainPositions = new HashSet<Point>[variableCount];

        orientations = new ArrayList<Orientation>();
        defaultOrientations();
        
        domainModels[0] = models;

        //add all positions in the room
        for (int i = 0; i < room.getHeight(); ++i) {
            for (int j = 0; j < room.getWidth(); ++j)
                domainPositions[0].add(new Point(i,j));
        }
        
        currentPosition = null;
        currentOrientation = null;
        currentModel = null;
        
        this.constraints = constraints;
    }


    //Pre: we have not iterated through all domain values yet.
    @Override
    public Value getNextDomainValue() {
        
        //1) iterate
        if (currentPosition == null) {
            //first iteration case
            positionIterator = domainPositions[0].iterator();
            orientationIterator = orientations.iterator();
            modelIterator = domainModels[0].iterator();
            
            currentPosition = (Point) positionIterator.next();
            currentModel = (FurnitureModel) modelIterator.next();
            currentOrientation = (Orientation) orientationIterator.next();
        }
        else if (positionIterator.hasNext()) {
            currentPosition = (Point) positionIterator.next();
        }
        else if (orientationIterator.hasNext()) {
            positionIterator = domainPositions[iteration].iterator();
            currentPosition = (Point) positionIterator.next();
            currentOrientation = (Orientation) orientationIterator.next();
        }
        else if (modelIterator.hasNext()) {
            positionIterator = domainPositions[iteration].iterator();
            currentPosition = (Point) positionIterator.next();
            orientationIterator = orientations.iterator();
            currentOrientation = (Orientation) orientationIterator.next();
            currentModel = (FurnitureModel) modelIterator.next();
        }
        else {
            throw new UnsupportedOperationException("There are no more domain values");
        }
        
        //2) return the new current value
        OrientedRectangle area = new OrientedRectangle(currentPosition,
            currentModel.getSize(), currentOrientation);
        
        return new FurnitureValue(area, currentModel);
    }

    
    //Pre: the 3 iterators point to valid values
    @Override
    public boolean hasMoreValues() {
        return modelIterator.hasNext() || positionIterator.hasNext() ||
               orientationIterator.hasNext();
    }

    
    @Override
    public void assignValue(Value value) {
        isAssigned = true;
        assignedValue = value;
        //Duda: no deberia crearse una copia de value?
    }

    
    @Override
    public void undoAssignValue() {
        isAssigned = false;
        assignedValue = null;        
    }

    
    @Override
    public void trimDomain(Variable variable) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    @Override
    public void undoTrimDomain(Variable variable, Value value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    @Override
    public boolean isAssigned() {
        return isAssigned;
    }

    
    @Override
    public Value getAssignedValue() {
        return assignedValue;
    }

    
    private void defaultOrientations() {
        orientations.add(Orientation.N);
        orientations.add(Orientation.E);
        orientations.add(Orientation.S);
        orientations.add(Orientation.W);
    }	
	

//    /**
//     * Adds a new constraint dinamically, so that it can be checked before the
//     * algorithm starts.
//     * @param newConstr the new constraint.
//     */
//    //pre: iteration = 0
//    public void applyModelConstraint(ModelConstraint newConstr) {
//        constraints.add(newConstr);
//        
//        Iterator startingModels = domainModels[0].iterator();
//        
//        while (startingModels.hasNext()) {
//            if (!newConstr.isSatisfied(startingModels.next()))
//                startingModels.remove();
//        }
//    }
//
//    public void removeModelConstraint(ModelConstraint oldConstr) {
//        constraints.remove(oldConstr);
//        // Here I've got to figure out how to restore the removed models
//    }
//    
//
//    public void restrictOrientation(Orientation o) {
//        orientations.clear();
//        orientations.add(o);
//    }
//    
//    
//    public void restrictArea(Boolean[][] intersect) {
//        int width = (positions[0].length == intersect[0].length) ? positions[0].length : 0;
//        int height = (positions.length == intersect.length) ? positions.length : 0;
//        
//        for (int i = 0; i < height; i++)
//            for (int j = 0; j < width; j++)
//                positions[i][j] = positions[i][j] && intersect[i][j];
//    }
      
}