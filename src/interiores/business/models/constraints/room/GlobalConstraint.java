
package interiores.business.models.constraints.room;

import interiores.business.models.constraints.Constraint;
import interiores.business.models.constraints.room.global.BudgetConstraint;
import interiores.business.models.constraints.room.global.ConnectivityConstraint;
import interiores.business.models.constraints.room.global.EnoughSpaceConstraint;
import interiores.business.models.constraints.room.global.SameColorConstraint;
import interiores.business.models.constraints.room.global.SameMaterialConstraint;
import interiores.business.models.constraints.room.global.SpaceRespectingConstraint;
import interiores.business.models.constraints.room.global.SpecificColorConstraint;
import interiores.business.models.constraints.room.global.SpecificMaterialConstraint;
import interiores.business.models.constraints.room.global.UnfitModelsPseudoConstraint;
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
@XmlSeeAlso({BudgetConstraint.class, ConnectivityConstraint.class, EnoughSpaceConstraint.class,
             SameColorConstraint.class, SameMaterialConstraint.class, SpaceRespectingConstraint.class,
             SpecificColorConstraint.class, SpecificMaterialConstraint.class, UnfitModelsPseudoConstraint.class})
public abstract class GlobalConstraint
    extends Constraint {
    
    private static Map<String, Class<? extends Constraint>> availableConstraints = new TreeMap();
    
    public static void addConstraintClass(String name, Class<? extends GlobalConstraint> constraintClass)
    {
        addConstraintClass(availableConstraints, name, constraintClass);
    }
    
    public static Class<? extends GlobalConstraint> getConstraintClass(String name)
            throws BusinessException
    {
        return (Class<? extends GlobalConstraint>) getConstraintClass(availableConstraints, name, "global");
    }
    
    public static Collection<String> getConstraintNames() {
        return availableConstraints.keySet();
    }
    
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
}
