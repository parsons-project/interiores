package interiores.business.models;

import interiores.business.models.catalogs.PersistentIdObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents a type of room, defined by a name, the type of furniture it should have,
 * and the type furniture it can't have
 * @author alvaro
 */
@XmlRootElement
public class RoomType
    extends PersistentIdObject
{    
    /**
     * Set containing all the FurnitureTypes that must be in this type of room
     */
    @XmlElementWrapper
    private HashSet<String> mustHave;
    
    /**
     * Set containing all the FurnitureTypes that can't be in this type
     */
    @XmlElementWrapper
    private HashSet<String> cantHave;
    
    public RoomType() {
        this(null);
    }
    
    /**
     * Simple creator of the room type
     * @param name The name of the room type
     */
    public RoomType(String name) {
        this(name, new ArrayList(), new ArrayList());
    }
    
    /**
     * Full creator of the room type
     * @param name The name of the room type
     * @param mustHave A collection of the furniture types that a room of this type
     *                 must have
     * @param cantHave A collection of the furniture types that a room of this type
     *                 cannot contain
     */    
    public RoomType(String name, Collection<FurnitureType> mustHave,
                    Collection<FurnitureType> cantHave) {
        super(name);
        this.mustHave = new HashSet();
        this.cantHave = new HashSet();
        
        for(FurnitureType fType : mustHave)
            this.mustHave.add(fType.getId());
        
        for(FurnitureType fType : cantHave)
            this.cantHave.add(fType.getId());
    }
    
    /**
     * Get the identifier of the room type
     * @return The name of the room type
     */
    public String getName() {
        return identifier;        
    }
    
    /**
     * Get a hash set of the names of the funiture types that this type of room must contain. 
     * @return A hash set of the names of the funiture types that this type of room must contain.
     */
    public HashSet<String> getMandatory() {
        return mustHave;
    }
    
    /**
     * Adds a furniture type to the set of the mandatory furniture types it must contain
     * @param fType The furniture type to add to the mandatory set 
     */
    public void addToMandatory(FurnitureType fType) {
        mustHave.add(fType.getId());
    }
    
    /**
     * Removes the furniture type of the set of the mandatory furniture types. If it wasn't
     * present it is ignored. Either way the set will not contain the type.
     * @param fType The furniture type to be removed 
     */
    public void removeFromMandatory(FurnitureType fType) {
        cantHave.remove(fType.getId());
    }
    
    /**
     * Removes the furniture type of the set of the mandatory furniture types. If it wasn't
     * present it is ignored. Either way the set will not contain the type.
     * @param fTypename The furniture type name to be removed 
     */
    public void removeFromMandatory(String fTypename) {
        cantHave.remove(fTypename);
    }
    
    /**
     * Get a hash set of the names of the funiture types that this type of room can't contain. 
     * @return A hash set of the names of the funiture types that this type of room can't contain.
     */
    public HashSet<String> getForbidden() {
        return cantHave;
    }
    
    /**
     * Adds a furniture type to the set of the mandatory furniture types it can't contain
     * @param fType The furniture type to add to the forbidden set
     */
    public void addToForbidden(FurnitureType fType) {
        cantHave.add(fType.getId());
    }
    
    /**
     * Removes the furniture type of the set of the forbidden furniture types. If it wasn't
     * present it is ignored. Either way the set will not contain the type.
     * @param fTypename The furniture type name to be removed 
     */
    public void removeFromForbidden(FurnitureType fType) {
        cantHave.remove(fType.getId());
    }
    
     /**
     * Removes the furniture type of the set of the forbidden furniture types. If it wasn't
     * present it is ignored. Either way the set will not contain the type.
     * @param fTypename The furniture type name to be removed 
     */
    public void removeFromForbidden(String fTypename) {
        cantHave.remove(fTypename);
    }
    
    /**
     * Checks if a fueniture type is mandatory or not
     * @param ftype The furniture type to be checked
     * @return true if ftype is in the mandatory set, false otherwise
     */
    public boolean isMandatory(FurnitureType ftype) {
        return mustHave.contains(ftype.getId());
    }
    
     /**
     * Checks if a fueniture type is forbidden or not
     * @param ftype The furniture type to be checked
     * @return true if ftype is in the forbidden set, false otherwise
     */
    public boolean isForbidden(FurnitureType ftype) {
        return cantHave.contains(ftype.getId());
    }
    
    @Override
    public String toString() {
        return identifier;
    }
}
