package interiores.business.models;

import interiores.business.models.constraints.UnaryConstraint;
import java.util.Collection;
import java.util.HashMap;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * This class is the abstraction of a wanted element for the room
 * @author alvaro
 */
public abstract class WantedElement {
    @XmlAttribute
    protected String name;
    
    @XmlAttribute
    protected String typeName;
    
    /*
     * The unary constraints related to this element
     */
    @XmlElementWrapper
    protected HashMap<Class, UnaryConstraint> constraints;
    
    /**
     * Simple creator. Creates a wanted element given its type
     * @param ft The funiture type of this wanted element
     */
    public WantedElement(String name, String typeName) {
        this.name = name;
        this.typeName = typeName;
        constraints = new HashMap();
    }
    
    public String getName() {
        return name;
    }
    
    public String getTypeName() {
        return typeName;
    }
    
    /**
     * Adds a unary constraint to the given wanted element
     * @param type The type of the constraint
     * @param unaryConstraint The unary constraint itself
     */
    public void addUnaryConstraint(UnaryConstraint unaryConstraint) {
        constraints.put(unaryConstraint.getClass(), unaryConstraint);
    }
    
    /**
     * Removes a unary constraint to the given wanted element
     * @param unaryConstraintClass The type of the constraint
     */
    public void removeUnaryConstraint(Class<? extends UnaryConstraint> unaryConstraintClass) {
        constraints.remove(unaryConstraintClass);
    }
    
    /**
     * Returns the unary constraint of the given type.
     * @param unaryConstraintClass
     * @return the constraint of the given type
     */
    public UnaryConstraint getUnaryConstraint(Class<? extends UnaryConstraint> unaryConstraintClass) {
        return constraints.get(unaryConstraintClass);
    }
    
    /**
     * Returns all the unary constraints applied to the wanted element.
     * @return the set of unary constraints
     */
    public Collection<UnaryConstraint> getUnaryConstraints() {
        return constraints.values();
    }   
}
