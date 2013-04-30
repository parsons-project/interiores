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
 *
 * @author larribas
 */
public class WantedFurniture {
    
    private String identifier;
    private FurnitureType type;
    
    private Map<String, UnaryConstraint> constraints;
    
    public WantedFurniture(FurnitureType ft, int id) {
        type = ft;
        identifier = type.getName() + id;
        constraints = new HashMap();
    }
    
    public void addConstraint(String type, UnaryConstraint constr) {
        constraints.put(type, constr);
    }
    
    public void removeConstraint(String type) {
        constraints.remove(type);
    }
    
    public UnaryConstraint getConstraint(String ctype) {
        return constraints.get(ctype);
    }
    
    public Collection getConstraints() {
        return constraints.values();
    }
    
    public FurnitureType getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return identifier;
    }
    
    public String getID() {
        return identifier;
    }
    
    
}
