package interiores.business.models.constraints;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hector
 */
public class ConstraintIndex<T extends Constraint>
{
    private List<Class> unaryConstraints;
    
    public ConstraintIndex() {
        unaryConstraints = new ArrayList();
    }
    
    public void add(Constraint unaryConstraint, Map constraints) {
        unaryConstraints.add(unaryConstraint.getClass());
        constraints.put(unaryConstraint.getClass(), unaryConstraint);
    }
    
    public void remove(Class<? extends T> unaryConstraintClass, Map constraints) {
        constraints.remove(unaryConstraintClass);
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
