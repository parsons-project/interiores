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

/**
* FurnitureVariable takes the role of variable in the context of the Constraint
* Satisfaction Problem (CSP) for the room design. A variable in this context
* has:
*  - A domain of values it can take.
*  - A set of constraints that affect the variable.
* Furthermore, a variable can be in two states:
*  - Assigned. When it has already been set to a certain value from the domain.
*  - Unassigned. When it does not have a certain value.
*/         
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
    
    
    // The set of orientation is not included explicitly as it is a static set.

    
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
    private Iterator modelIterator;
    
    private Point currentPosition;
    private Orientation currentOrientation;
    private FurnitureModel currentModel;
    
 
    
    
    /**
    * Constructor.
    */
    public FurnitureVariable(List<FurnitureModel> models, Room room, int variableCount) {
        isAssigned = false;
        iteration = 0;

        domainModels = new List<Model>[variableCount];
        domainPositions = new HashSet<Point>[variableCount];

        positionIterator = domainPositions[0].iterator();
        modelIterator = domainModels[0].iterator();

        currentPosition = null;
        currentOrientation = Orientation.N;
        currentModel = null;
        

    }

    // Implementation from the abstract superclass

    @Override
    //Pre: we have not iterated through all domain values yet.
    public Value getNextDomainValue() {
        
        //1) iterate
        if (currentPosition == null) {
            //first iteration case
            currentPosition = (Point) positionIterator.next();
            currentModel = (FurnitureModel) modelIterator.next();
            currentOrientation = Orientation.N;
        }
        else if (positionIterator.hasNext()) {
            currentPosition = (Point) positionIterator.next();
        }
        else if (currentOrientation != Orientation.W) {
            positionIterator = domainPositions[iteration].iterator();
            currentPosition = (Point) positionIterator.next();
            currentOrientation.rotateRight();
        }
        else if (modelIterator.hasNext()) {
            positionIterator = domainPositions[iteration].iterator();
            currentPosition = (Point) positionIterator.next();
            currentOrientation = Orientation.N;
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
        return modelIterator.hasNext();
        //Note: this implementation is dependent of the order the sets are
        //iterated (i.e., only works if models are iterated last)
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

	
	

//IMPLEMENTACION DE LORENZO; TO BE MERGED BY NIL
    
    // DOMAIN
    List<FurnitureModel> models;     // a list of FurnitureModels
    List<Orientation> orientations;  // a list of allowed orientations
    Boolean[][] positions;           // a boolean matrix of valid positions

    public FurnitureVariable(List<FurnitureModel> models, Room room) {
        
        this.models = models;
        
        this.positions = new Boolean[room.getHeight()][room.getWidth()];
        for (Boolean[] b : positions) Arrays.fill(b, Boolean.TRUE);
        currentPosition = new Point(0,0);
        
        // Inclusion of every possible orientation (pending review)
        orientations = new ArrayList<Orientation>();
        defaultOrientations();
        
        constraints = new ArrayList<ModelConstraint>();
    }

    
    // Do I have to implement this function with a Value parameter instead of a FurnitureValue one??
    @Override
    public void setAssignedValue(Value value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void applyConstraint(ModelConstraint newConstr) {
        constraints.add(newConstr);
        
        int i = 0;
        while ( i < models.size()) {
            if ( newConstr.isSatisfied(models.get(i)) ) i++;
            else models.remove(i);
        }
    }
    
    public void restrictOrientation(Orientation o) {
        orientations.clear();
        orientations.add(o);
    }
    
    public void restrictArea(Boolean[][] intersect) {
        int width = (positions[0].length == intersect[0].length) ? positions[0].length : 0;
        int height = (positions.length == intersect.length) ? positions.length : 0;
        
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                positions[i][j] = positions[i][j] && intersect[i][j];
    }
    
    // Only for testing purposes
    public int getConstraints() {
        return models.size();
    }

//    public void removeConstraint(ModelConstraint oldConstr) {
//        constraints.remove(oldConstr);
//        // Here I've got to figure out how to restore the removed models
//    }
    
    
    private void defaultOrientations() {
        orientations.add(Orientation.N);
        orientations.add(Orientation.W);
        orientations.add(Orientation.E);
        orientations.add(Orientation.S);
    }
    
    private void iterateToNextValue() {
        int roomWidth = positions[0].length;
        int roomHeight = positions.length;
        
        do {
            currentPosition.x++;
            if (currentPosition.x >= roomWidth) {
                currentPosition.x = 0;
                currentPosition.y++;
                if (currentPosition.y >= roomHeight) {
                    currentPosition.y = 0;
                    currentOrientation++;
                    if (currentOrientation >= orientations.size()) {
                        currentOrientation = 0;
                        currentModel++;
                    }
                }
            }
        } while ( currentModel < models.size() && !positions[currentPosition.y][currentPosition.x] );
    }
    
    
}