package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Orientation;
import interiores.business.models.constraints.ModelConstraint;
import interiores.shared.Value;
import interiores.shared.Variable;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
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
    Polygon positions;               // a shape representing the allowed positions

    // N.B. For now, the positions are represented as a rectangle.
    // My intention is to use an extended shape in the future.
    
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
    
    
    public FurnitureVariable(List<FurnitureModel> models, Polygon positions) {
        
        this.models = models;
        
        this.positions = positions;
        currentPosition = new Point(0,0);
        
        // Inclusion of every possible orientation (pending review)
        orientations = new ArrayList<Orientation>();
        orientations.add(Orientation.N);
        orientations.add(Orientation.W);
        orientations.add(Orientation.E);
        orientations.add(Orientation.S);
    }
    
    @Override
    public FurnitureValue getNextDomainValue() {
        Rectangle r = positions.getBounds();
        currentPosition.x++;
        if ( currentPosition.x > (r.x + r.width) ) {
            currentPosition.x = 0;
        }
        
    }

    @Override
    public boolean hasMoreValues() {
        Rectangle r = positions.getBounds();
        return currentModel < models.size() - 1
                || currentOrientation < orientations.size() - 1
                || (currentPosition.x <= (r.x + r.width) 
                    && currentPosition.y <= (r.y + r.height ) );
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

    // ??
    @Override
    public void setAssignedValue(Value value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
	
	
	