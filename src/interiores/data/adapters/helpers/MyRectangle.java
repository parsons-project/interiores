package interiores.data.adapters.helpers;

import interiores.data.adapters.PointAdapter;
import java.awt.Point;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author hector
 */
@XmlRootElement
public class MyRectangle {
    @XmlElement
    @XmlJavaTypeAdapter(PointAdapter.class)
    public Point location;
    
    @XmlAttribute
    public int width;
    
    @XmlAttribute
    public int height;
}
