package interiores.business.models.constraints;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author hector
 */
public class ConstraintIndex<T extends Constraint>
{
    private Set<Class> unaryConstraints;
    
    public ConstraintIndex() {
        unaryConstraints = new HashSet();
    }
    
    public void add(Constraint unaryConstraint, Map constraints) {
        unaryConstraints.add(unaryConstraint.getClass());
        constraints.put(unaryConstraint.getClass(), unaryConstraint);
    }
    
    public void remove(Class<? extends T> unaryConstraintClass, Map constraints) {
        constraints.remove(unaryConstraintClass);
        unaryConstraints.remove(unaryConstraintClass);
    }
    
    public T get(Class<? extends T> unaryConstraintClass, Map constraints) {
        return (T) constraints.get(unaryConstraintClass);
    }

    public Collection<T> getAll(Map constraints) {
        Collection<T> result = new ArrayList();
        
        for(Class ucClass : unaryConstraints)
            result.add(get(ucClass, constraints));
            
        return result;
    }
}
