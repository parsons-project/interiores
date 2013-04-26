package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.business.models.Room;
import interiores.business.models.constraints.ModelConstraint;
import interiores.shared.Value;
import interiores.shared.Variable;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class FurnitureVariable extends Variable
{
    
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
    
    public void restrictArea(boolean[][] intersect) {
        int width = (positions[0].length == intersect[0].length) ? positions[0].length : 0;
        int height = (positions.length == intersect.length) ? positions.length : 0;
        
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                positions[i][j] = positions[i][j] && intersect[i][j];
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
            if (currentPosition.x > roomWidth) {
                currentPosition.x = 0;
                currentPosition.y++;
                if (currentPosition.y > roomHeight) {
                    currentPosition.y = 0;
                    currentOrientation++;
                    if (currentOrientation == orientations.size()) {
                        currentOrientation = 0;
                        currentModel++;
                    }
                }
            }
        } while ( !positions[currentPosition.y][currentPosition.x] && currentModel < models.size() );
    }
    
    
}
	
	
	