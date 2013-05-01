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
    
    private FurnitureType type;
    
    private Map<String, UnaryConstraint> constraints;
    
    public WantedFurniture(FurnitureType ft) {
        type = ft;
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
    
    public String getTypeName() {
        return type.getName();
    }
    
}
