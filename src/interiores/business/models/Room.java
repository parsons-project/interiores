package interiores.business.models;

import interiores.core.business.BusinessException;
import interiores.core.business.Model;
import interiores.utils.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
        return size.depth;
    }
    
    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap();
        
        map.put("type", type.getName());
        map.put("width", (int) size.width);
        map.put("height", (int) size.depth);
        
        return map;
    }
}
