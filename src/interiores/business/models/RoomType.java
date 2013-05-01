package interiores.business.models;

import interiores.business.models.catalogs.PersistentIdObject;
import interiores.utils.Dimension;
import java.util.TreeSet;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents a type nof room, defined by a name, the type of furniture it should have,
 * and the type furniture it can't have
 * @author alvaro
 */
@XmlRootElement
public class RoomType
    extends PersistentIdObject
{
    /**
     * Minimum dimensions that a room of this type must have
     */
    @XmlElement
    private Dimension minDimension;
    
    /**
     * Set containing all the FurnitureTypes that must be in this type of room
     */
    @XmlElementWrapper
    private TreeSet<String> mustHave;
    
    /**
     * Set containing all the FurnitureTypes that can't be in this type
     */
    @XmlElementWrapper
    private TreeSet<String> cantHave;
    
    public RoomType() {
        this(null);
    }
    
    /**
     * Simple creator of the room type
     * @param name The name of the room type
     */
    public RoomType(String name) {
        this(name, new Dimension(), new String[0], new String[0]);
    }
    

    /**
     * Full creator of the room type
     * @param name The name of the room type
     * @param mustHave A collection of the furniture types that a room of this type
     *                 must have
     * @param cantHave A Vector of strings of the furniture types that a room of this type
     *                 cannot contain
     */    
    public RoomType(String name, Dimension minDimension, String[] mustHave, String[] cantHave) {
        super(name);
        
        this.minDimension = minDimension;
        this.mustHave = new TreeSet();
        this.cantHave = new TreeSet();
        
        for(int i = 0; i < mustHave.length; ++i)
            this.mustHave.add(mustHave[i]);
        
        for(int i = 0; i < cantHave.length; ++i)
            this.cantHave.add(cantHave[i]);
    }
    
    /**
     * Get the identifier of the room type
     * @return The name of the room type
     */
    public String getName() {
        return identifier;        
    }

    public Dimension getMinimumDimension() {
        return minDimension;
    }
    
    /**
     * Get a tree set of the names of the funiture types that this type of room must contain. 
     * @return A tree set of the names of the funiture types that this type of room must contain.
     */    
    public TreeSet<String> getMandatory() {
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
        removeFromMandatory(fType.getId());
    }
    
    /**
     * Removes the furniture type of the set of the mandatory furniture types. If it wasn't
     * present it is ignored. Either way the set will not contain the type.
     * @param fTypename The furniture type name to be removed 
     */
    public void removeFromMandatory(String fTypename) {
        mustHave.remove(fTypename);
    }
    
    /**
     * Get a tree set of the names of the funiture types that this type of room can't contain. 
     * @return A tree set of the names of the funiture types that this type of room can't contain.
     */
    public TreeSet<String> getForbidden() {
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
        removeFromForbidden(fType.getId());
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
