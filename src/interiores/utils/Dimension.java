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
    
    @Override
    public String toString() {
        return "(" + width + ", " + depth + ")";
    }
}
