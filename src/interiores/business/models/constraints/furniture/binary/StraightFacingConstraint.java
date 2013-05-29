package interiores.business.models.constraints.furniture.binary;

import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.business.models.backtracking.area.Area;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.furniture.BinaryConstraintEnd;
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
        OrientedRectangle r = ((FurnitureValue) otherVariable.getAssignedValue()).getArea();
        

        r.enlarge(MAXDIST, r.getOrientation());
        //r is the area where the variable must be partially placed
        //to satisfy the constraint
        
        //we can reduce it to the single line corresponding to the center of the
        //retangle
        
        OrientedRectangle validRectangle;
        if (r.getOrientation() == Orientation.N) 
            validRectangle = new OrientedRectangle(r.getCenter().x-1,
                                                   r.y - MAXDIST,
                                                   2, //2 units wide to leave room for rounding errors, not sure if necessary
                                                   MAXDIST,
                                                   Orientation.S);
        else if (r.getOrientation() == Orientation.S) 
            validRectangle = new OrientedRectangle(r.getCenter().x-1,
                                                   r.y + r.height,
                                                   2, 
                                                   MAXDIST,
                                                   Orientation.N);
        else if (r.getOrientation() == Orientation.E)
            validRectangle = new OrientedRectangle(r.x + r.width,
                                                   r.getCenter().y-1,
                                                   MAXDIST,
                                                   2,
                                                   Orientation.W);
        else 
            validRectangle = new OrientedRectangle(r.x - MAXDIST,
                                                   r.getCenter().y-1,
                                                   MAXDIST,
                                                   2,
                                                   Orientation.E);
   
        //it must be expanded to W and N to include all positions such that there
        //is a model that placed there, is at least parcially in validRectangle
        int maxSize = variable.getMaxSize();
        validRectangle= validRectangle.enlarge(maxSize, Orientation.W);
        validRectangle= validRectangle.enlarge(maxSize, Orientation.N);
 
        
        variable.trimExceptP(new Area(validRectangle.enlarge(MAXDIST, validRectangle.getOrientation())));
        HashSet<Orientation> validOrientations = new HashSet<Orientation>();
        validOrientations.add(validRectangle.getOrientation().complementary());
        variable.trimExceptO(validOrientations);
    }
    
    @Override
    public String toString() {
        return "Straight facing with " + otherVariable.getName();
    }
}  
