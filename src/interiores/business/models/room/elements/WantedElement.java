package interiores.business.models.room.elements;

import interiores.business.models.constraints.UnaryConstraint;
import java.util.Collection;
import java.util.HashMap;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * This class is the abstraction of a wanted element for the room
 * @author alvaro
 */
public abstract class WantedElement {
    @XmlAttribute
    protected String name;
    
    @XmlAttribute
    protected String typeName;
    
    /*
     * The unary constraints related to this element
     */
    @XmlElementWrapper
    protected HashMap<Class, UnaryConstraint> constraints;
    
    /**
     * Simple creator. Creates a wanted element given its type
     * @param ft The funiture type of this wanted element
     */
    public WantedElement(String name, String typeName) {
        this.name = name;
        this.typeName = typeName;
        constraints = new HashMap();
    }
    
    public WantedElement(String typeName) {
        this(typeName, typeName);
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getTypeName() {
        return typeName;
    }
}
