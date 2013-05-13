/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models;

import interiores.business.models.constraints.UnaryConstraint;
import java.util.Collection;
import java.util.HashMap;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * This class represents a furniture that the user wants to add to the room
 * @author larribas
 */
public class WantedFurniture
{
    @XmlAttribute
    private String name;
    
    @XmlAttribute
    private String furnitureTypeName;
    
    /*
     * The unary constraints related to this furniture
     */
    @XmlElementWrapper
    private HashMap<Class, UnaryConstraint> constraints;
    
    /**
     * Simple creator. Creates a wanted furniture given its type
     * @param ft The funiture type of this wanted furniture
     */
    public WantedFurniture(String name, String furnitureTypeName) {
        this.name = name;
        this.furnitureTypeName = furnitureTypeName;
        constraints = new HashMap();
    }
    
    public String getName() {
        return name;
    }
    
    public String getTypeName() {
        return furnitureTypeName;
    }
    
    /**
     * Adds a unary constraint to the given wanted furniture
     * @param type The type of the constraint
     * @param unaryConstraint The unary constraint itself
     */
    public void addUnaryConstraint(UnaryConstraint unaryConstraint) {
        constraints.put(unaryConstraint.getClass(), unaryConstraint);
    }
    
    /**
     * Removes a unary constraint to the given wanted furniture
     * @param unaryConstraintClass The type of the constraint
     */
    public void removeUnaryConstraint(Class unaryConstraintClass) {
        constraints.remove(unaryConstraintClass);
    }
    
    /**
     * Returns the unary constraint of the given type.
     * @param unaryConstraintClass
     * @return the constraint of the given type
     */
    public UnaryConstraint getUnaryConstraint(Class unaryConstraintClass) {
        return constraints.get(unaryConstraintClass);
    }
    
    /**
     * Returns all the unary constraints applied to the wanted piece of furniture.
     * @return the set of unary constraints
     */
    public Collection getUnaryConstraints() {
        return constraints.values();
    } 
}
