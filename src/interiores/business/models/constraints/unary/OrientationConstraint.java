package interiores.business.models.constraints.unary;

import interiores.business.models.Orientation;
import interiores.business.models.backtracking.Domain;
import interiores.business.models.constraints.UnaryConstraint;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * OrientationConstraint represents a constraint imposed over the orientation of
 * a piece of furniture.
 * The furniture piece will only be able to take an orientation from the list
 * of the constraint.
 */
@XmlRootElement
public class OrientationConstraint
    extends UnaryConstraint {
    
    /**
     * validOrientations contains the orientations a piece of furniture can have
     * in order to satisfy the constraint.
     */
    @XmlElementWrapper
    private List<Orientation> validOrientations;
    
    
    public OrientationConstraint() {
        
    }
    
    public OrientationConstraint(Orientation o) {
        validOrientations = new ArrayList();
        validOrientations.add(o);
    }
    
    /**
     * Creates an orientation constraint such that only those pieces of
     * furniture matching one of the given orientations will satisfy it
     * @param validOrientations The orientations that define the constraint
     */
    public OrientationConstraint(List<Orientation> validOrientations) {
        this.validOrientations = validOrientations;
    }
    
    /**
     * Eliminates all values that do not fulfil the constraint.
     * @param variable The variable to be checked.
     */
    @Override
    public void eliminateInvalidValues(Domain domain) {
        domain.getOrientations(0).clear();
        domain.getOrientations(0).addAll(validOrientations);
    }
    
    /**
     * Modifies the orientations defined for the constraint.
     * @param newOrientations The orientations that will override the previous ones
     */
    public void changeOrientation(List<Orientation> newOrientations) {
        validOrientations = newOrientations;
    }
    
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        
        result.append(this.getClass().getName() + NEW_LINE);
        
        result.append("Valid orientations: ");
        for (Orientation orientation : validOrientations) {
            result.append(orientation.toString() + " ");
        }
        result.append(NEW_LINE);
        return result.toString();
    }
}

