package horarios.shared;

import interiores.business.models.FurnitureType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * IdObject represents a object with a identifier
 * @author Jaume
 */
@XmlRootElement
@XmlSeeAlso({FurnitureType.class})
public class IdObject implements Comparable<IdObject> {
    
    /**
     * Object identifier
     */
    @XmlAttribute
    protected String identifier;
    
    public IdObject() {
        this(null);
    }
    
    /**
     * Constructor with a specific id
     * @param id Identifier of the object
     */
    public IdObject(String id) {
        identifier = id;
    }
    
    
    /**
     * Getter of the object identifier
     * @return Identifier of the object
     */
    public String getId() {
        return identifier;
    }
    
    
    /**
     * compareTo function overrided 
     * @param o Second object to do the comparation
     * @return <0 -> this < o; =0 -> this = o; >0 -> this > o
     */
    @Override
    public int compareTo(IdObject o) {
        return this.identifier.compareTo(o.getId());
    }
}
