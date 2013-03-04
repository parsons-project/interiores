package interiores.models;

import interiores.core.mvc.Model;

/**
 *
 * @author hector
 */
public class Room extends Model {
    private String name;
    private int width;
    private int height;
    
    public Room()
    {
        
    }
    
    public void initDefaults()
    {
        name = "Sin nombre";
        width = 400;
        height = 300;
    }
    
    public String getName()
    {
        return name;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public void setName(String name)
    {
        String oldName = this.name;
        
        this.name = name;
        fireChange("name", oldName, name);
    }
    
    public void setWidth(int width)
    {
        int oldWidth = this.width;
        
        this.width = width;
        fireChange("width", oldWidth, width);
    }
    
    public void setHeight(int height)
    {
        int oldHeight = this.height;
        
        this.height = height;
        fireChange("height", oldHeight, height);
    }
}
