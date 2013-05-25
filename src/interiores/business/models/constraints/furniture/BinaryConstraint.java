package interiores.business.models.constraints.furniture;

import interiores.business.models.OrientedRectangle;
import interiores.business.models.Room;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.backtracking.InterioresVariable;
import interiores.business.models.constraints.Constraint;
import interiores.business.models.constraints.furniture.binary.MaxDistanceConstraint;
import interiores.business.models.constraints.furniture.binary.MinDistanceConstraint;
import interiores.core.business.BusinessException;
import interiores.utils.Dimension;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author alvaro
 */
@XmlRootElement
@XmlSeeAlso({MaxDistanceConstraint.class, MinDistanceConstraint.class})
public abstract class BinaryConstraint
    extends Constraint implements InexhaustiveTrimmer, BacktrackingTimeTrimmer {
    
    private static Map<String, Class<? extends InexhaustiveTrimmer>> availableConstraints = new TreeMap();
    
    public static void addConstraintClass(String name, Class<? extends BinaryConstraint> constraintClass)
    {
        addConstraintClass(availableConstraints, name, constraintClass);
    }
    
    public static Class<? extends BinaryConstraint> getConstraintClass(String name)
            throws BusinessException
    {
        return (Class<? extends BinaryConstraint>) getConstraintClass(availableConstraints, name, "binary");
    }
    
    public static Collection<String> getConstraintClasses() {
        return availableConstraints.keySet();
    }
    
    
    
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    
    protected InterioresVariable otherVariable;
    
    protected BinaryConstraint(InterioresVariable otherVariable) {
        this.otherVariable = otherVariable;
    }
    
    /**
     * Given 2 variables, of which the first one has an assigned value, trims
     * the domain of the second variable according to the constraint.
     * @param assignedVariable
     * @param toTrimVariable
     * @param room
     */
    @Override
    public final void trim(FurnitureVariable variable) {
        if (otherVariable.isAssigned())
            trim2(variable);
    }
 
    public abstract void trim2(FurnitureVariable variable);

    @Override
    public final boolean isSatisfied(FurnitureVariable variable) {
        if (! otherVariable.isAssigned())
            return true;
        else return isSatisfied2(variable);
    }

    public abstract boolean isSatisfied2(FurnitureVariable variable);
    
    /**
     * Returns a estimation of the impact (wheight) of the constraint. The more
     * restrictive, the higher the weight.
     * @return 
     */
    public abstract int getWeight();
    
    public final InterioresVariable getOtherVariable() {
        return otherVariable;
    }

}
