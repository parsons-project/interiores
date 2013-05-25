
package interiores.business.models.constraints.room;

import interiores.business.models.constraints.furniture.*;
import interiores.business.models.backtracking.Domain;
import interiores.business.models.constraints.furniture.unary.AreaConstraint;
import interiores.business.models.constraints.furniture.unary.ColorConstraint;
import interiores.business.models.constraints.furniture.unary.MaterialConstraint;
import interiores.business.models.constraints.furniture.unary.ModelConstraint;
import interiores.business.models.constraints.furniture.unary.OrientationConstraint;
import interiores.business.models.constraints.furniture.unary.PriceConstraint;
import interiores.business.models.constraints.furniture.unary.SizeRangeConstraint;
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
public abstract class GlobalConstraint
    extends InexhaustiveConstraint implements PreliminarTrimmer
{
    private static Map<String, Class<? extends InexhaustiveConstraint>> availableConstraints = new TreeMap();
    
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
    
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
}
