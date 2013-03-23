package interiores.business.models;

import interiores.core.business.Model;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hector
 */
public class Room extends Model {
    private String type;
    private int width;
    private int height;
    
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
