package interiores.business.models.constraints.furniture.binary;

import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.business.models.backtracking.area.Area;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.furniture.BinaryConstraintEnd;
import java.util.HashSet;

/**
 * The variable associated with the constraint has to be facing the
 * otherVariable, and can be at much MAXDIST away.
 * The variables are considered facing each other if there exists a paralel
 * line to the direction either of them are facing that crosses
 * the area of both variables.
 * @author alvaro
 */
public class PartialFacingConstraint
    extends BinaryConstraintEnd
{
    private static final int MAXDIST = 10000;
    
    public PartialFacingConstraint() {
        super();
    }
    
    @Override
    public boolean isSatisfied2(FurnitureVariable variable) {
        OrientedRectangle rectangle1 = ((FurnitureValue) otherVariable.getAssignedValue()).getArea();
        OrientedRectangle rectangle2 = ((FurnitureValue) variable.getAssignedValue()).getArea();
        
        return rectangle1.enlarge(MAXDIST, rectangle1.getOrientation()).intersects(rectangle2) &&
               rectangle2.enlarge(MAXDIST, rectangle2.getOrientation()).intersects(rectangle1);
        
    }
    
    @Override
    public int getWeight() {
        return 110;
    }

    @Override
    public void trim2(FurnitureVariable variable) {
        OrientedRectangle validRectangle = ((FurnitureValue) otherVariable.getAssignedValue()).getArea();
        
        Orientation curOrientation = validRectangle.getOrientation();
        
        validRectangle = validRectangle.enlarge(MAXDIST, curOrientation);
        //validRectangle is the area where the variable must be partially placed
        //to satisfy the constraint
        
        //it must be expanded to W and N to include all positions such that there
        //is a model that placed there, is at least parcially in validRectangle
        int maxSize = variable.getMaxSize();
        validRectangle = validRectangle.enlarge(maxSize, curOrientation.rotateLeft());
        validRectangle = validRectangle.enlarge(maxSize, curOrientation.rotateRight());
 
        
        variable.trimExceptP(new Area(validRectangle));
        HashSet<Orientation> validOrientations = new HashSet<Orientation>();
        validOrientations.add(validRectangle.getOrientation().complementary());
        variable.trimExceptO(validOrientations);
    }

}
