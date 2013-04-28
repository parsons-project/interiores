package horarios.shared;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * IdObject represents a object with a identifier
 * @author Jaume
 */
@XmlRootElement
abstract public class IdObject<IdType> implements Comparable<IdObject> {
    
    /**
     * Object identifier
     */
    @XmlAttribute
    protected IdType identifier;
    
    
    /**
     * Constructor with a specific id
     * @param id Identifier of the object
     */
    public IdObject(IdType id) {
        identifier = id;
    }
    
    
    /**
     * Getter of the object identifier
     * @return Identifier of the object
     */
    public IdType getId() {
        return identifier;
    }
}
