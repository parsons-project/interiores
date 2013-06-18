package interiores.utils;

import interiores.business.exceptions.InvalidValueException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hector
 */
@XmlRootElement
public class Range {
    @XmlAttribute
    public int min;
    
    @XmlAttribute
    public int max;
    
    public Range() throws InstantiationException {
        this(0, 0);
    }
    
    public Range(int min, int max) throws InvalidValueException {
        if (min > max) throw new InvalidValueException("Max should be greater or equal than min");
        
        this.min = min;
        this.max = max;
    }
    
    public int average() {
        return (min + max) / 2;
    }
    
    @Override
    public String toString() {
        return "min=" + min + ", max=" + max;
    }
}
