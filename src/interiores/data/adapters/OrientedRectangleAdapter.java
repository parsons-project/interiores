package interiores.data.adapters;

import interiores.business.models.OrientedRectangle;
import interiores.data.adapters.helpers.MyOrientedRectangle;
import interiores.data.adapters.helpers.MyRectangle;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author hector
 */
public class OrientedRectangleAdapter extends XmlAdapter<MyOrientedRectangle, OrientedRectangle>
{   
    @Override
    public MyOrientedRectangle marshal(OrientedRectangle orientedRectangle) {
        MyOrientedRectangle myOrientedRectangle = new MyOrientedRectangle();
        MyRectangle myRectangle = new MyRectangle();
        
        myRectangle.location = orientedRectangle.getLocation();
        myRectangle.width = (int) orientedRectangle.getWidth();
        myRectangle.height = (int) orientedRectangle.getHeight();
        
        myOrientedRectangle.rectangle = myRectangle;
        myOrientedRectangle.orientation = orientedRectangle.getOrientation();
        
        return myOrientedRectangle;
    }
    
    @Override
    public OrientedRectangle unmarshal(MyOrientedRectangle myOrientedRectangle) {
        return new OrientedRectangle(
                myOrientedRectangle.rectangle.location.x,
                myOrientedRectangle.rectangle.location.y,
                myOrientedRectangle.rectangle.width,
                myOrientedRectangle.rectangle.height,
                myOrientedRectangle.orientation);
    }
}
