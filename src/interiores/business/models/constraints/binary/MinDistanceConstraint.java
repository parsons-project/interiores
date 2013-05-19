package interiores.business.models.constraints.binary;

import interiores.business.models.OrientedRectangle;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.InterioresVariable;
import interiores.business.models.constraints.BinaryConstraint;
import interiores.core.Debug;
import interiores.utils.Dimension;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Constraint representing a minimum separation between two variables
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
    
    /**
     * Checks whether two variables satisfy the constraint
     * @param fvariable1 The first variable
     * @param fvariable2 The second variable
     * @return 'true' if the two variables are separated by, at least, 'distance' cm. 'false' otherwise
     */
    @Override
    public boolean isSatisfied(InterioresVariable fvariable1, InterioresVariable fvariable2) {
        
//        Debug.println("F1: " + fvariable1.getID());
//        Debug.println("F2: " + fvariable2.getID());
//        
        
        OrientedRectangle rectangle1 = ((FurnitureValue) fvariable1.getAssignedValue()).getArea();
        OrientedRectangle rectangle2 = ((FurnitureValue) fvariable2.getAssignedValue()).getArea();
//        
//        Debug.println("R1: " + rectangle1);
//        Debug.println("R2: " + rectangle2);
//        
//        Debug.println("Enlarged: " + rectangle1.enlarge(distance));
//        
//        Debug.println("Colision?: " + rectangle1.enlarge(distance).intersects(rectangle2));
//        
        return (!rectangle1.enlarge(new Dimension(distance,0)).intersects(rectangle2) &&
                !rectangle1.enlarge(new Dimension(0, distance)).intersects(rectangle2) &&
                !rectangle1.enlarge(new Dimension(distance/4, 3*distance/4)).intersects(rectangle2) &&
                !rectangle1.enlarge(new Dimension(distance/2, distance/2)).intersects(rectangle2) &&
                !rectangle1.enlarge(new Dimension(3 * distance/4, distance/4)).intersects(rectangle2));
    }
    
    @Override
    public String toString() {
        return "Minimum distance = " + distance + "cm constraint";
    }
}
