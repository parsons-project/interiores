package interiores.data.adapters.helpers;

import interiores.business.models.Orientation;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hector
 */
@XmlRootElement
public class MyOrientedRectangle {
    @XmlElement
    public MyRectangle rectangle;
    
    @XmlAttribute
    public Orientation orientation;
}
