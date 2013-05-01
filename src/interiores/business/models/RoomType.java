package interiores.business.models;

import interiores.business.models.catalogs.PersistentIdObject;
import interiores.utils.Dimension;
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
    
    public RoomType(String name) {
        this(name, new Dimension(), new String[0], new String[0]);
    }
    
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
    
    public String getName() {
        return identifier;        
    }
    
    public Dimension getMinimumDimension() {
        return minDimension;
    }
    
    public TreeSet<String> getMandatory() {
        return mustHave;
    }
    
    public void addToMandatory(FurnitureType fType) {
        mustHave.add(fType.getId());
    }
    
    public void removeFromMandatory(FurnitureType fType) {
        removeFromMandatory(fType.getId());
    }
    
    public void removeFromMandatory(String fTypename) {
        mustHave.remove(fTypename);
    }
    
    public TreeSet<String> getForbidden() {
        return cantHave;
    }
    
    public void addToForbidden(FurnitureType fType) {
        cantHave.add(fType.getId());
    }
    
    public void removeFromForbidden(FurnitureType fType) {
        removeFromForbidden(fType.getId());
    }
    
    public void removeFromForbidden(String fTypename) {
        cantHave.remove(fTypename);
    }
    
    // should these methods go here or maybe in the controller
    public boolean isMandatory(FurnitureType ftype) {
        return mustHave.contains(ftype.getId());
    }
    
    public boolean isForbidden(FurnitureType ftype) {
        return cantHave.contains(ftype.getId());
    }
    
    @Override
    public String toString() {
        return identifier;
    }
}
