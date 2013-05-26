package interiores.data.adapters;

import interiores.data.adapters.helpers.MyRectangle;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author hector
 */
public class RectangleAdapter extends XmlAdapter<MyRectangle, Rectangle>
{   
    @Override
    public MyRectangle marshal(Rectangle rectangle) {
        MyRectangle myRectangle = new MyRectangle();
        
        myRectangle.location = rectangle.getLocation();
        myRectangle.width = (int) rectangle.getWidth();
        myRectangle.height = (int) rectangle.getHeight();
        
        return myRectangle;
    }
    
    @Override
    public Rectangle unmarshal(MyRectangle myRectangle) {
        return new Rectangle(myRectangle.location, new Dimension(myRectangle.width, myRectangle.height));
    }
}
