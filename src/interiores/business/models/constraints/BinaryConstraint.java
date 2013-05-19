package interiores.business.models.constraints;

import interiores.business.models.backtracking.InterioresVariable;
import interiores.business.models.constraints.binary.MaxDistanceConstraint;
import interiores.business.models.constraints.binary.MinDistanceConstraint;
import interiores.core.business.BusinessException;
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
    extends Constraint
{
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
    
    public abstract boolean isSatisfied(InterioresVariable fvariable1, InterioresVariable fvariable2);
    
    public int getPriority() {
        return 1;
    }
}
