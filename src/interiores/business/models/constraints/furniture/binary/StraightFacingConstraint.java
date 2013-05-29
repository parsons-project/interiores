package interiores.business.models.constraints.furniture.binary;

import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.business.models.backtracking.area.Area;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.furniture.BinaryConstraintEnd;
import interiores.utils.Dimension;
import java.util.HashSet;

/**
 *
 * @author alvaro
 */
public class StraightFacingConstraint
    extends BinaryConstraintEnd {
    
    private static final int MAXDIST = 1000;
    
    
    public StraightFacingConstraint() {
        super();
    }
    
    
    @Override
    public boolean isSatisfied2(FurnitureVariable variable) {
        
        OrientedRectangle rectangle1 = ((FurnitureValue) otherVariable.getAssignedValue()).getArea();
        OrientedRectangle rectangle2 = ((FurnitureValue) variable.getAssignedValue()).getArea();
        
        return rectangle1.enlarge(MAXDIST, rectangle1.getOrientation()).intersects(rectangle2) &&
               rectangle2.enlarge(MAXDIST, rectangle2.getOrientation()).intersects(rectangle1) &&
               (rectangle1.getCenter().x == rectangle2.getCenter().x ||
                rectangle1.getCenter().y == rectangle2.getCenter().y);
        
    }
    
    @Override
    public int getWeight() {
        return 140;
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
        int otherMaxSize = ((FurnitureVariable) otherVariable).getMaxSize();
        Dimension modelSize = variable.getAssignedValue().getModel().getSize();
        int thisMaxSize;
        switch(curOrientation) {
            case E:
            case W:
                thisMaxSize = modelSize.depth;
            default:
                thisMaxSize = modelSize.width;
        }
        
        validRectangle = validRectangle.enlarge(otherMaxSize - thisMaxSize/2, curOrientation.rotateLeft());
        validRectangle = validRectangle.enlarge(otherMaxSize - thisMaxSize/2, curOrientation.rotateRight());
 
        
        variable.trimExceptP(new Area(validRectangle));
        HashSet<Orientation> validOrientations = new HashSet<Orientation>();
        validOrientations.add(validRectangle.getOrientation().complementary());
        variable.trimExceptO(validOrientations);
    }
    
    @Override
    public String toString() {
        return "Straight facing with " + otherVariable.getName();
    }
}  
