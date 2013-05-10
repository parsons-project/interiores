/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models;

import interiores.business.models.constraints.UnaryConstraint;
import java.util.Collection;
import java.util.TreeMap;
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
    private TreeMap<String, UnaryConstraint> constraints;
    
    /**
     * Simple creator. Creates a wanted furniture given its type
     * @param ft The funiture type of this wanted furniture
     */
    public WantedFurniture(String name, String furnitureTypeName) {
        this.name = name;
        this.furnitureTypeName = furnitureTypeName;
        constraints = new TreeMap();
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
     * @param constr The unary constraint itself
     */
    public void addUnaryConstraint(String type, UnaryConstraint constr) {
        constraints.put(type, constr);
    }
    
    /**
     * Removes a unary constraint to the given wanted furniture
     * @param type The type of the constraint
     */
    public void removeUnaryConstraint(String type) {
        constraints.remove(type);
    }
    
    /**
     * Returns the unary constraint of the given type.
     * @param ctype
     * @return the constraint of the given type
     */
    public UnaryConstraint getUnaryConstraint(String ctype) {
        return constraints.get(ctype);
    }
    
    /**
     * Returns all the unary constraints applied to the wanted piece of furniture.
     * @return the set of unary constraints
     */
    public Collection getUnaryConstraints() {
        return constraints.values();
    } 
}
