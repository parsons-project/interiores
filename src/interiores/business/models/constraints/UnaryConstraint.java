
package interiores.business.models.constraints;

import interiores.business.models.backtracking.Domain;
import interiores.business.models.constraints.unary.AreaConstraint;
import interiores.business.models.constraints.unary.ColorConstraint;
import interiores.business.models.constraints.unary.MaterialConstraint;
import interiores.business.models.constraints.unary.ModelConstraint;
import interiores.business.models.constraints.unary.OrientationConstraint;
import interiores.business.models.constraints.unary.PriceConstraint;
import interiores.business.models.constraints.unary.SizeRangeConstraint;
import interiores.core.business.BusinessException;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Generic class for representing constraints over a furniture piece.
 */
@XmlRootElement
@XmlSeeAlso({AreaConstraint.class, ColorConstraint.class, MaterialConstraint.class, ModelConstraint.class,
    OrientationConstraint.class, PriceConstraint.class, SizeRangeConstraint.class})
public abstract class UnaryConstraint
    extends Constraint
{
    private static Map<String, Class<? extends Constraint>> availableConstraints = new TreeMap();
    
    public static void addConstraintClass(String name, Class<? extends UnaryConstraint> constraintClass)
    {
        addConstraintClass(availableConstraints, name, constraintClass);
    }
    
    public static Class<? extends UnaryConstraint> getConstraintClass(String name)
            throws BusinessException
    {
        return (Class<? extends UnaryConstraint>) getConstraintClass(availableConstraints, name, "unary");
    }
    
    public static Collection<String> getConstraintNames() {
        return availableConstraints.keySet();
    }
    
     /**
     * Eliminates all values from the variables' domain that do not fulfill the
     * restriction.
     * @param variable The specific variable that has to pass the constraint
     */
    public abstract void eliminateInvalidValues(Domain domain);
}
