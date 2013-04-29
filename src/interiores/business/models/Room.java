package interiores.business.models;

import interiores.core.business.Model;
import java.awt.Dimension;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hector
 */
@XmlRootElement
public class Room extends Model {
    @XmlAttribute
    private RoomType type;
    
    @XmlAttribute
    private Dimension size;
        
    public Room(RoomType type, Dimension size) {
        this.type = type;
        this.size = size;
    }
    
    public Room(RoomType type, int width, int height) {
        this(type,new Dimension(width, height) );
    }
    
    public RoomType getType() {
        return type;
    }
    
    public Dimension getDimension() {
        return size;
    }
    
    public int getWidth() {
        return size.width;
    }
    
    public int getHeight() {
        return size.height;
    }
}
