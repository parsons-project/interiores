package interiores.business.models;

import interiores.business.models.catalogs.PersistentIdObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import javax.xml.bind.annotation.XmlAttribute;
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
    @XmlAttribute
    private String name; // name of the type
    
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
    
    public RoomType(String name) {
        this(name, new ArrayList(), new ArrayList());
    }
    
    public RoomType(String name, Collection<String> mustHave, Collection<String> cantHave) {
        this.name = name;
        this.mustHave = new HashSet(mustHave);
        this.cantHave = new HashSet(cantHave);
    }
    
    public String getName() {
        return name;        
    }
    
    public HashSet<String> getObligatory() {
        return mustHave;
    }
    
    public HashSet<String> getForbidden() {
        return cantHave;
    }
    
    // should these methods go here or maybe in the controller
    public boolean isObligatory(FurnitureType ftype) {
        return mustHave.contains(ftype.getId());
    }
    
    public boolean isForbidden(FurnitureType ftype) {
        return cantHave.contains(ftype.getId());
    }
            
            
}
