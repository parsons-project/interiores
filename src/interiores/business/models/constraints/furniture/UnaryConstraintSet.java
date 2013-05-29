package interiores.business.models.constraints.furniture;

import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author hector
 */
public class UnaryConstraintSet
{
    private HashMap<Class<? extends UnaryConstraint>, UnaryConstraint> unaryConstraints;
    
    public UnaryConstraintSet() {
        unaryConstraints = new HashMap();
    }
    
    public void add(UnaryConstraint unaryConstraint) {
        unaryConstraints.put(unaryConstraint.getClass(), unaryConstraint);
    }
    
    public void remove(Class<? extends UnaryConstraint> unaryConstraintClass) {
        unaryConstraints.remove(unaryConstraintClass);
    }
    
    public UnaryConstraint get(Class<? extends UnaryConstraint> unaryConstraintClass) {
        return (UnaryConstraint) unaryConstraints.get(unaryConstraintClass);
    }

    public Collection<UnaryConstraint> getAll() {
        return unaryConstraints.values();
    }
}
