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
    List<FurnitureModel>[] domainModels;

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
    HashSet<Point>[] domainPositions;
    
    /**
    * Orientation: not included as it is a static set
    */


    
    /**
    * Constructor. I don't know what parameters this has...
    */
    public FurnitureVariable(int variableCount) {
        assignedValue = null;
        domainModels = new List<Model>[variableCount];
        domainPositions = new HashSet<Point>[variableCount];
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

	
	





/**
 * Represents a furniture variable in the context of the algorithm.
 * A variable in such a context has:
 *  - A domain of possible values it can take.
 *  - A list of constraints that affect the variable
 * 
 * Furthermore, a variable can take two states:
 *  - Assigned. When it has already been set to a certain value.
 *    This means the algorithm has already taken this variable into account
 *  - Unassigned. When it does not have a certain value.
 * @author larribas
 */


//IMPLEMENTACION DE LORENZO; TO BE MERGED BY NIL
    
    // DOMAIN
    // The domain is represented by
    List<FurnitureModel> models;     // a list of FurnitureModels
    List<Orientation> orientations;  // a list of allowed orientations
    Boolean[][] positions;           // a boolean matrix of valid positions

    // N.B. For now, the positions are represented as a boolean matrix.
    // My intention is to use a shape in the future.
    
    // ITERATION OVER THE DOMAIN
    // This section is a first approach to the iteration over all the possible
    // values in the domain. Iteration is done in this order: 1) Models, 2) Orientation, 3) Position
    int currentModel;
    int currentOrientation;
    Point currentPosition;
    
    // CONSTRAINTS
    List<ModelConstraint> constraints;
    
    // ASSGINATION
    boolean isAssigned = false;
    FurnitureValue assignedValue;    
    
    
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
    
    @Override
    public FurnitureValue getNextDomainValue() {
        
        // Supposedly, the iterator points to a valid domain value
        OrientedRectangle area = new OrientedRectangle( currentPosition,
                                                        models.get(currentModel).getSize(),
                                                        orientations.get(currentOrientation) );
        
        return new FurnitureValue(area, models.get(currentModel));
    }

    @Override
    public boolean hasMoreValues() {
        if (currentModel < models.size()) iterateToNextValue();
        return currentModel < models.size();
    }


    public void setAssignedValue(FurnitureValue value) {
        isAssigned = true;
        assignedValue = value;        
    }

    @Override
    public void unsetValue() {
        isAssigned = false;
        assignedValue = null;
    }

    @Override
    public void trimDomain(Variable variable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void undoTrimDomain(Variable variable, Value value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAssigned() {
        return isAssigned;
    }

    @Override
    public FurnitureValue getAssignedValue() {
        return assignedValue;
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