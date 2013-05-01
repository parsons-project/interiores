/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models;

import interiores.business.models.constraints.BinaryConstraint;
import interiores.core.Debug;
import interiores.core.business.BusinessException;
import interiores.utils.BinaryConstraintAssociation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author larribas
 */
public class WishList {
    
    private Map<String,WantedFurniture> furniture;
    private Map<String,BinaryConstraintAssociation> binaryConstraints;
    
    public WishList() {
        furniture = new HashMap();
        binaryConstraints = new HashMap();
    }
    
    public void addWantedFurniture(WantedFurniture f) {
        int i = 1;
        while (furniture.containsKey(f.getTypeName() + i)) i++;
        furniture.put(f.getTypeName() + i, f);
    }
    
    public void removeWantedFurniture(String id) {
        furniture.remove(id);
    }
    
    public void addBinaryConstraint(String ctype, BinaryConstraint bc, String f1, String f2) {
        String key = existsBinaryConstraint(ctype, f1, f2);
        if (key == null) {
            BinaryConstraintAssociation bca = new BinaryConstraintAssociation();
            bca.furniture1 = f1;
            bca.furniture2 = f2;
            bca.constraint = bc;
            binaryConstraints.put(ctype+f1+f2,bca);
        }
        else binaryConstraints.get(key).constraint = bc;       
    }
    
    public void removeBinaryConstraint(String ctype, String f1, String f2) {
        String key = existsBinaryConstraint(ctype, f1, f2);
        binaryConstraints.remove(key);
    }
    
    public Collection getConstraints(String furnitureID) {
        List<Object> result = new ArrayList();
        
        // First, we add all the unary constraints defined over that piece of furniture
        result.addAll(furniture.get(furnitureID).getConstraints());
        
        // Second, we add all the binary constraints related to that piece of furniture
        Object[] keys = binaryConstraints.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            String k = (String) keys[i];
            if (k.contains(furnitureID)) result.add(binaryConstraints.get(k));
        }
        
        return result;
    }
    
    public List getBinaryConstraints() {
        return new ArrayList(binaryConstraints.values());
    }
    
    public Collection getFurnitureNames() {
        return furniture.keySet();
    }
    
    public WantedFurniture getWantedFurniture(String id) {
        return furniture.get(id);
    }
    
    public Collection getWantedFurniture() {
        return furniture.values();
    }
    
    private String existsBinaryConstraint(String ctype, String f1, String f2) {
        if (binaryConstraints.containsKey(ctype+f1+f2)) return ctype+f1+f2;
        else if (binaryConstraints.containsKey(ctype+f2+f1)) return ctype+f2+f1;
        else return null;
    }
}
