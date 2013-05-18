package interiores.utils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hector
 */
@XmlRootElement
public class Dimension
{
    public static enum Component { WIDTH, DEPTH };
    
    @XmlAttribute
    public int width;
    
    @XmlAttribute
    public int depth;
    
    public Dimension() {
        this(0, 0);
    }
    
    public Dimension(int width, int depth) {
        this.width = width;
        this.depth = depth;
    }
    
    /**
     * Checks if dimension is between the given ranges
     * @param widthRange The width range
     * @param depthRange The depth range
     * @return True if the dimension is between the given ranges, false otherwise
     */
    public boolean isBetween(Range widthRange, Range depthRange) {
        return isWidthBetween(widthRange) && isDepthBetween(depthRange);
    }
    
    public boolean isBetween(Component component, Range componentRange) {
        if(component == Component.WIDTH)
            return isWidthBetween(componentRange);
        
        return isDepthBetween(componentRange);
    }
    
    public boolean isWidthBetween(Range widthRange) {
        return (widthRange.min <= width && widthRange.max >= width);
    }
    
    public boolean isDepthBetween(Range depthRange) {
        return (depthRange.min <= depth && depthRange.max >= depth);
    }
    
    @Override
    public String toString() {
        return "(" + width + ", " + depth + ")";
    }
}
