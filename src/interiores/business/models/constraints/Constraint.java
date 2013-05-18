package interiores.business.models.constraints;

import interiores.core.business.BusinessException;
import java.util.Map;

/**
 *
 * @author hector
 */
abstract public class Constraint
{
    public static void addConstraintClass(Map<String, Class<? extends Constraint>> availableConstraints,
            String name, Class<? extends Constraint> constraintClass)
    {
        availableConstraints.put(name, constraintClass);
    }
    
    public static Class<? extends Constraint> getConstraintClass(
            Map<String, Class<? extends Constraint>> availableConstraints, String name, String type)
            throws BusinessException
    {
        if(! availableConstraints.containsKey(name))
            throw new BusinessException("There is no " + type + " constraint known as " + name);
        
        return availableConstraints.get(name);
    }
}
