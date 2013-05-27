package interiores.business.models;

import interiores.business.exceptions.ForbiddenFurnitureException;
import interiores.business.exceptions.MandatoryFurnitureException;
import interiores.business.models.catalogs.PersistentIdObject;
import interiores.business.models.room.FurnitureType;
import interiores.core.Utils;
import interiores.utils.Dimension;
import interiores.utils.Range;
import java.util.TreeSet;
import javax.xml.bind.annotation.XmlElement;
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
    private static final int MAX_WIDTH = 1000;
    private static final int MAX_DEPTH = 1000;
    
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
        this(null,0,0);
    }
    
    /**
     * Simple creator of the room type
     * @param name The name of the room type
     */
    public RoomType(String name, int width, int depth) {
        this(name, new Dimension(width,depth), new String[0], new String[0]);
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
    
    public String getFullName() {
        String[] words = getName().split("(?=[A-Z])");
        
        String fullName = Utils.capitalize(words[0]);
        
        for(int i = 1; i < words.length; ++i)
            fullName += " " + Utils.decapitalize(words[i]);
        
        return fullName;
    }
    
    public void setName(String n) {
        super.identifier = n;
    }

    public Range getWidthRange() {
        return new Range(minDimension.width, MAX_WIDTH);
    }
    
    public Range getDepthRange() {
        return new Range(minDimension.depth, MAX_DEPTH);
    }
    
    public void setMinWidth(int w) {
        minDimension = new Dimension(w,minDimension.depth);
    }
    
    public void setMinDepth(int d) {
        minDimension = new Dimension(minDimension.width,d);
    }
    
    public boolean isSizeValid(Dimension size) {
        return size.isBetween(getWidthRange(), getDepthRange());
    }
    
    /**
     * Get a tree set of the names of the furniture types that this type of room must contain. 
     * @return A tree set of the names of the furniture types that this type of room must contain.
     */    
    public TreeSet<String> getMandatory() {
        return mustHave;
    }
    
    /**
     * Adds a furniture type to the set of the mandatory furniture types it must contain
     * @param fType The furniture type to add to the mandatory set 
     */
    public void addToMandatory(FurnitureType fType) {
        String fId = fType.getId();
        if (cantHave.contains(fId)) throw new ForbiddenFurnitureException(fId, getName());
        
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
    
    public void clearMandatory() {
        mustHave.clear();
    }
    
    public void clearForbidden() {
        cantHave.clear();
    }
    
    /**
     * Get a tree set of the names of the furniture types that this type of room can't contain. 
     * @return A tree set of the names of the furniture types that this type of room can't contain.
     */
    public TreeSet<String> getForbidden() {
        return cantHave;
    }
    
    /**
     * Adds a furniture type to the set of the mandatory furniture types it can't contain
     * @param fType The furniture type to add to the forbidden set
     */
    public void addToForbidden(FurnitureType fType) {
        String fId = fType.getId();
        if (mustHave.contains(fId)) throw new MandatoryFurnitureException(fId);
        
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
    
    public boolean isMandatory(String fTypeName) {
        return mustHave.contains(fTypeName);
    }
    
    /**
     * Checks if a furniture type is mandatory or not
     * @param ftype The furniture type to be checked
     * @return true if ftype is in the mandatory set, false otherwise
     */
    public boolean isMandatory(FurnitureType ftype) {
        return mustHave.contains(ftype.getId());
    }
    
    public boolean isForbidden(String fTypeName) {
        return cantHave.contains(fTypeName);
    }
    
     /**
     * Checks if a furniture type is forbidden or not
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
