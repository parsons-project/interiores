package interiores.business.models;

import interiores.core.business.BusinessException;
import interiores.utils.Dimension;
import interiores.utils.Range;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a room.
 * A room belongs to a certain type and is of a determined size
 * @author hector
 */
@XmlRootElement
public class Room
{
    // The topmost dimensions the application supports
    private static final int MAX_WIDTH = 1000;
    private static final int MAX_DEPTH = 1000;
    
    @XmlAttribute
    private RoomType type;
    
    @XmlAttribute
    private Dimension size;
    
    public Room() {
        
    }
    
    /**
     * Creates a new room
     * @param type The type of room this one belongs to
     * @param size The size of this room
     * @throws BusinessException 
     */
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
    
    /**
     * Creates a new room
     * @param type The type of room this one belongs to
     * @param width The width of this room
     * @param depth The depth of this room
     * @throws BusinessException 
     */
    public Room(RoomType type, int width, int depth)
            throws BusinessException
    {
        this(type, new Dimension(width, depth));
    }
    
    /**
     * Gets the type of the room
     * @return RoomType of this room
     */
    public RoomType getType() {
        return type;
    }
    
    /**
     * Gets the dimension or size of the room
     * @return Dimension object representing the size of this room
     */
    public Dimension getDimension() {
        return size;
    }
    
    public int getWidth() {
        return size.width;
    }
    
    public int getDepth() {
        return size.depth;
    }
    
    /**
     * Converts the characteristics of the room into a map
     * @return A Map containing the features that define the room
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap();
        
        map.put("type", type.getName());
        map.put("width", (int) size.width);
        map.put("depth", (int) size.depth);
        
        return map;
    }
}
