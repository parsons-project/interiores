package interiores.business.models;

import interiores.core.business.Model;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hector
 */
@XmlRootElement
public class Room extends Model {
    @XmlAttribute
    private String type;
    
    @XmlAttribute
    private int width;
    
    @XmlAttribute
    private int height;
    
    public Room()
    {
        
    }
    
    public Room(String type, int width, int height)
    {
        this.type = type;
        this.width = width;
        this.height = height;
    }
    
    public String getType()
    {
        return type;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }
}
