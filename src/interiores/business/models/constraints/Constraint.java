/**
 * 
 * Package structure
 * 
 * constraints:
 * Constraint (this class)
 * 
 * constraints.furniture:
 * The interfaces of furniture constraints and the subclasses of Constraint from
 * which furniture constraints inherit (UnaryConstraint and BinaryConstraint).
 * 
 * constraints.furniture.unary:
 * The actual subclasses of UnaryConstraint.
 * 
 * constraints.furniture.binary:
 * The actual subclasses of BinaryConstraint.
 * 
 * constraints.room:
 * The interfaces of room constraints and the subclass of Constraint from which
 * room constraints inherit (GlobalConstraint).
 * 
 * constraints.room.global:
 * The actual subclasses of GlobalConstraint.
 */
package interiores.business.models.constraints;

import interiores.business.models.constraints.furniture.BinaryConstraintEnd;
import interiores.business.models.constraints.furniture.UnaryConstraint;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.core.business.BusinessException;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * A Constraint is a class that represents any form of restriction upon the
 * values the variables might take.
 * @author Nil
 */
@XmlRootElement
@XmlSeeAlso({GlobalConstraint.class, BinaryConstraintEnd.class, UnaryConstraint.class})
public abstract class Constraint
{    
    public static void addConstraintClass(Map<String,
            Class<? extends Constraint>> availableConstraints,
            String name, Class<? extends Constraint> constraintClass) {
        
        availableConstraints.put(name, constraintClass);
    }
    
    public static Class<? extends Constraint> getConstraintClass(
            Map<String, Class<? extends Constraint>> availableConstraints,
            String name, String type)            
            throws BusinessException {
        
        if(! availableConstraints.containsKey(name))
            throw new BusinessException("There is no " + type + " constraint known as " + name);
        
        return availableConstraints.get(name);
    }
}
