package interiores.business.models.constraints.binary;

import interiores.business.models.OrientedRectangle;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.BinaryConstraint;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This binary constraint represents 
 * @author alvaro
 */
@XmlRootElement
public class MinDistanceConstraint 
                    extends BinaryConstraint {
    
    @XmlAttribute
    private int distance; // The minimum distance between the two variables
    
    public MinDistanceConstraint() {
        
    }
    
    public MinDistanceConstraint(int distance) {
        this.distance = distance;
    }
    
    @Override
    public boolean isSatisfied(FurnitureVariable fvariable1, FurnitureVariable fvariable2) {
        
        OrientedRectangle rectangle1 = ((FurnitureValue) fvariable1.getAssignedValue()).getArea();
        OrientedRectangle rectangle2 = ((FurnitureValue) fvariable2.getAssignedValue()).getArea();
        
        return !rectangle1.enlarge(distance).intersects(rectangle2);
    }
    
    @Override
    public String toString() {
        return "Minimum distance = " + distance + "cm constraint";
    }
}
