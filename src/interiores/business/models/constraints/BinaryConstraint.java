package interiores.business.models.constraints;

import interiores.business.models.OrientedRectangle;
import interiores.business.models.Room;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.backtracking.InterioresVariable;
import interiores.business.models.constraints.binary.MaxDistanceConstraint;
import interiores.business.models.constraints.binary.MinDistanceConstraint;
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
    extends Constraint {
    
    private static Map<String, Class<? extends Constraint>> availableConstraints = new TreeMap();
    
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
    
    
    /**
     * Given 2 variables, of which the first one has an assigned value, trims
     * the domain of the second variable according to the constraint.
     * @param assignedVariable
     * @param toTrimVariable
     * @param room
     */
    public abstract void trim(InterioresVariable assignedVariable, FurnitureVariable toTrimVariable, OrientedRectangle roomArea);
    
    /**
     * Given 2 variables, none of which has a value, eliminates values of the domain of
     * elther that can not fulfil the constraint.
     */
    public abstract void preliminarTrim(InterioresVariable variable1, InterioresVariable variable2, OrientedRectangle roomArea);
    
    /**
     * Returns a estimation of the impact (wheight) of the constraint. The more
     * restrictive, the higher the weight.
     * @return 
     */
    abstract public int getWeight(OrientedRectangle roomArea);
    
    /**
     * Returns whether a the constraint is satisfied.
     * Both variables have an assigned value. 
    * @return 
     */
    abstract public boolean isSatisfied(InterioresVariable assignedVariable, FurnitureVariable variable);
}
