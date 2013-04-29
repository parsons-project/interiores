package interiores.business.models;

import interiores.core.business.BusinessException;
import interiores.core.business.Model;
import java.awt.Dimension;
import java.util.ArrayList;
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
    
    private List<FurnitureType> wishList;
    
        
    public Room(RoomType type, Dimension size) {
        this.type = type;
        this.size = size;
        wishList = new ArrayList();
    }
    
    public Room(RoomType type, int width, int height) {
        this(type,new Dimension(width, height) );
    }
    
    public void addFurnitureType(FurnitureType ft) {
        wishList.add(ft);
    }
    
    public void removeFurnitureType(FurnitureType ft) throws BusinessException {
        boolean exists = wishList.remove(ft);
        if (!exists)
            throw new BusinessException("The furniture type you want to remove is not in the list");
    }
    
    public List<FurnitureType> getWishList() {
        return wishList;
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
    
    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap();
        
        map.put("type", type.getName());
        map.put("width", (int) size.getWidth());
        map.put("height", (int) size.getHeight());
        
        return map;
    }
}
