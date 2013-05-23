package interiores.business.models.room.elements;

import interiores.business.models.constraints.UnaryConstraint;
import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * This class represents a furniture that the user wants to add to the room
 * @author larribas
 */
@XmlRootElement
public class WantedFurniture extends WantedElement
{
    public WantedFurniture()
    { }
    
    /**
     * Simple creator. Creates a wanted furniture given its type
     * @param ft The funiture type of this wanted furniture
     */
    public WantedFurniture(String furnitureTypeName) {
        super(furnitureTypeName);
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
