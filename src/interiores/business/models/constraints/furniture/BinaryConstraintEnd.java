package interiores.business.models.constraints.furniture;

import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.backtracking.InterioresVariable;
import interiores.business.models.constraints.Constraint;
import interiores.business.models.constraints.furniture.binary.MaxDistanceConstraint;
import interiores.business.models.constraints.furniture.binary.MinDistanceConstraint;
import interiores.business.models.constraints.furniture.binary.PartialFacingConstraint;
import interiores.business.models.constraints.furniture.binary.StraightFacingConstraint;
import interiores.core.Debug;
import interiores.core.business.BusinessException;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author alvaro
 */
@XmlRootElement
@XmlSeeAlso({MaxDistanceConstraint.class, MinDistanceConstraint.class, PartialFacingConstraint.class,
    StraightFacingConstraint.class})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BinaryConstraintEnd
    extends Constraint
    implements InexhaustiveTrimmer, BacktrackingTimeTrimmer, Cloneable
{
    private static Map<String, Class<? extends Constraint>> availableConstraints = new TreeMap();
    
    public static void addConstraintClass(String name, Class<? extends BinaryConstraintEnd> constraintClass)
    {
        addConstraintClass(availableConstraints, name, constraintClass);
    }
    
    public static Class<? extends BinaryConstraintEnd> getConstraintClass(String name)
            throws BusinessException
    {
        return (Class<? extends BinaryConstraintEnd>) getConstraintClass(availableConstraints, name, "binary");
    }
    
    public static Collection<String> getConstraintClasses() {
        return availableConstraints.keySet();
    }
    
    @XmlElement
    @XmlIDREF
    protected InterioresVariable otherVariable;
    
    public BinaryConstraintEnd()
    { }
    
    public BinaryConstraintEnd(InterioresVariable otherVariable) {
        this.otherVariable = otherVariable;
    }
    
    public void setOtherVariable(InterioresVariable otherVariable) {
        if(this.otherVariable != null)
            throw new UnsupportedOperationException("You can only set the other variable once.");
        
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
    public boolean shouldTrim(FurnitureVariable actual) {
        return actual.equals(otherVariable);
    }

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
    
    public boolean hasCounterPart() {
        return (!otherVariable.isConstant());
    }
    
    public BinaryConstraintEnd getCounterPart(FurnitureVariable start) {
        if(!hasCounterPart())
            throw new BusinessException("Binary constraint without counterpart.");
        
        FurnitureVariable end = (FurnitureVariable) otherVariable;
        
        if (end == start)
            throw new BusinessException("Can't have a restriction with itself");
        
        Debug.println("Creating constraint counterpart...");
        
        try {
            BinaryConstraintEnd otherEnd = (BinaryConstraintEnd) clone();
            otherEnd.otherVariable = start;
            
            return otherEnd;
        }
        catch(CloneNotSupportedException e) {
            throw new BusinessException("Impossible to create other binary constraint end.");
        }
    }
}
