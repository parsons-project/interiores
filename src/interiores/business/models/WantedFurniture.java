/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models;

import interiores.business.models.constraints.UnaryConstraint;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a furniture that the user wants to add to the room
 * @author larribas
 */
public class WantedFurniture {
    
    /*
     * The type of the furniture
     */
    private FurnitureType type;
    
    /*
     * The unary constraints related to this furniture
     */
    private Map<String, UnaryConstraint> constraints;
    
    /**
     * Simple creator. Creates a wanted furniture given its type
     * @param ft The funiture type of this wanted furniture
     */
    public WantedFurniture(FurnitureType ft) {
        type = ft;
        constraints = new HashMap();
    }
    
    /**
     * Adds a unary constraint to the given wanted furniture
     * @param type The type of the constraint
     * @param constr The unary constraint itself
     */
    public void addConstraint(String type, UnaryConstraint constr) {
        constraints.put(type, constr);
    }
    
    /**
     * Removes a unary constraint to the given wanted furniture
     * @param type The type of the constraint
     */
    public void removeConstraint(String type) {
        constraints.remove(type);
    }
    
    /**
     * Returns the unary constraint of the given type.
     * @param ctype
     * @return the constraint of the given type
     */
    public UnaryConstraint getConstraint(String ctype) {
        return constraints.get(ctype);
    }
    
    /**
     * Returns all the unary constraints applied to the wanted piece of furniture.
     * @return the set of unary constarints
     */
    public Collection getConstraints() {
        return constraints.values();
    }
    
    /**
     * Returns the type of the wanted piece of furniture.
     * @return the type of the WantedFurniture
     */
    public FurnitureType getType() {
        return type;
    }
    
    /**
     * Returns the name of the type of the wanted piece of furniture.
     * @return the name of the type of the WantedFurniture
     */
    public String getTypeName() {
        return type.getName();
    }
    
}
