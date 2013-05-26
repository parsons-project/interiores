package interiores.data.adapters;

import interiores.data.adapters.helpers.MyPoint;
import java.awt.Point;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author hector
 */
public class PointAdapter extends XmlAdapter<MyPoint, Point>
{   
    @Override
    public MyPoint marshal(Point point) {
        MyPoint myPoint = new MyPoint();
        
        myPoint.x = point.x;
        myPoint.y = point.y;
        
        return myPoint;
    }
    
    @Override
    public Point unmarshal(MyPoint myPoint) {
        return new Point(myPoint.x, myPoint.y);
    }
}
