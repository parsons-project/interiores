
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
 * This class represents the bag of furniture types that the user wants to
 * include in the room and the constraints required upon them.
 * Internally, this class prepares the room set up to be passed to the
 * algorihm classes.
 * @author larribas
 */
public class WishList {
    
    /**
     * multi-set (set with repeated elements) of furniture
     * types that are suposed to fit in the room, known as WantedFurniture. 
     */
    private Map<String,WantedFurniture> furniture;
    
    /**
     * set of binary constraints that affect two WantedFurniture.
     */
    private Map<String,BinaryConstraintAssociation> binaryConstraints;
    
    /**
     * Default constructor.
     */
    public WishList() {
        furniture = new HashMap();
        binaryConstraints = new HashMap();
    }
    
    /**
     * Add a new WantedFurniture 
     * @param f the WantedFurniture to add
     */
    public void addWantedFurniture(WantedFurniture f) {
        int i = 1;
        while (furniture.containsKey(f.getTypeName() + i)) i++;
        furniture.put(f.getTypeName() + i, f);
    }
    
    /**
     * Removes a WantedFurniture.
     * @param id identifier of the WantedFurniture to remove. if there is no
     *           WantedFurntiteru with that ID, no action is performed.
     */
    public void removeWantedFurniture(String id) {
        furniture.remove(id);
    }
    
    /**
     * Adds a new binary constraint.
     * @param ctype The type of the constraint
     * @param bc The specific binary constraint to add
     * @param f1 First WantedFurniture affected by this constraint
     * @param f2 Second WantedFurniture affected by this constraint
     */
    public void addBinaryConstraint(String ctype, BinaryConstraint bc, String f1, String f2) {
        String key = getBinaryConstraintID(ctype, f1, f2);
        if (key == null) {
            BinaryConstraintAssociation bca = new BinaryConstraintAssociation();
            bca.furniture1 = f1;
            bca.furniture2 = f2;
            bca.constraint = bc;
            binaryConstraints.put(ctype+f1+f2,bca);
        }
        else binaryConstraints.get(key).constraint = bc;       
    }
    
    /**
     * Removes a specific binary constraint. Between 2 given WantedFurnitures,
     * there can only be one furniture of the same type.
     * @param ctype The type of the binary constraint
     * @param f1 First WantedFurniture affected by this constraint
     * @param f2 Second WantedFurniture affected by this constraint
     */
    public void removeBinaryConstraint(String ctype, String f1, String f2) {
        String key = getBinaryConstraintID(ctype, f1, f2);
        binaryConstraints.remove(key);
    }
    
    /**
     * Returns all the constraints (both unary and binary) of a given WantedFurniture.
     * @param furnitureID the identifier of the WantedFurniture
     * @return the set of constraints
     */
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
    
    /**
     * Returns all the binary constraints.
     * @return List containing all the binary constraints.
     */
    public List getBinaryConstraints() {
        return new ArrayList(binaryConstraints.values());
    }
    
    /**
     * Returns the identifiers of the WantedFurniture
     * @return the string identifying the WantedFurnitures
     */
    public Collection getFurnitureNames() {
        return furniture.keySet();
    }
    
    /**
     * Returns a particular WantedFurniture.
     * @param id the identifier 
     * @return the WantedFurniture with the identifier id
     */
    public WantedFurniture getWantedFurniture(String id) {
        return furniture.get(id);
    }
    
    /**
     * Returns the set of WantedFurnitures
     * @return the collection of WantedFurnitures
     */
    public Collection getWantedFurniture() {
        return furniture.values();
    }
    
    /**
     * Gets the identifier of the binary constraint of type ctype defined
     * over the two specified pieces of furniture
     * @param ctype The type of the constraint
     * @param f1 The first piece of furniture
     * @param f2 The second piece of furniture
     * @return A String containing the identifier of such a constraint, or
     * null if it doesn't exist
     */
    private String getBinaryConstraintID(String ctype, String f1, String f2) {
        if (binaryConstraints.containsKey(ctype+f1+f2)) return ctype+f1+f2;
        else if (binaryConstraints.containsKey(ctype+f2+f1)) return ctype+f2+f1;
        else return null;
    }
}
