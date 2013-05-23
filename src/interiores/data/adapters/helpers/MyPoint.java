package interiores.data.adapters.helpers;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hector
 */
@XmlRootElement
public class MyPoint {
    @XmlAttribute
    public int x;
    
    @XmlAttribute
    public int y;
}
