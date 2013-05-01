package interiores.business.models;

import interiores.core.business.BusinessException;
import interiores.utils.Dimension;
import interiores.utils.Range;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hector
 */
@XmlRootElement
public class Room
{
    private static final int MAX_WIDTH = 1000;
    private static final int MAX_DEPTH = 1000;
    
    @XmlAttribute
    private RoomType type;
    
    @XmlAttribute
    private Dimension size;
    
    public Room() {
        
    }
    
    public Room(RoomType type, Dimension size)
            throws BusinessException
    {
        Dimension minTypeDimension = type.getMinimumDimension();
        
        Range widthRange = new Range(minTypeDimension.width, MAX_WIDTH);
        Range depthRange = new Range(minTypeDimension.depth, MAX_DEPTH);
        
        if(! size.isBetween(widthRange, depthRange))
            throw new BusinessException("The room you are trying to create is not between the permitted "
                    + "dimension range. Width[" + widthRange + "], Depth[" + depthRange + "]");
        
        this.type = type;
        this.size = size;
    }
    
    public Room(RoomType type, int width, int depth)
            throws BusinessException
    {
        this(type, new Dimension(width, depth));
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
    
    public int getDepth() {
        return size.depth;
    }
    
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap();
        
        map.put("type", type.getName());
        map.put("width", (int) size.width);
        map.put("depth", (int) size.depth);
        
        return map;
    }
}
