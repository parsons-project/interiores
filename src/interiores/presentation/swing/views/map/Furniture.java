package interiores.presentation.swing.views.map;

import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.core.Debug;
import interiores.utils.CoolColor;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author hector0193
 */
public class Furniture
    extends RoomElement
{
    private static final Color COLOR_UNSELECTED = CoolColor.BLACK.getColor();
    private static final Color COLOR_SELECTED = CoolColor.BLUE.getColor();
    
    private String name;
    
    public Furniture(String name, OrientedRectangle area, Color color)
    {
        super(area, color);
        
        this.name = name;
    }
    
    public void setOrientation(Orientation o)
    {
        rectangle.setOrientation(o);
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        super.draw(g);
        
        Rectangle2D l = getOrientationMark();
        g.setColor(Color.black);
        g.fill(l);
        
        Rectangle2D r = g.getFontMetrics().getStringBounds(name, g);
        
        int stringW = (int) r.getWidth();
        int stringH = (int) r.getHeight();
        int startW = ((int)rectangle.getWidth())/2 - stringW/2;
        int startH = ((int)rectangle.getHeight())/2 + stringH/2;
        
        Color drawColor = (isSelected()) ? COLOR_SELECTED : COLOR_UNSELECTED;
        
        if(color == drawColor)
            drawColor = Color.white;
              
        g.setColor(drawColor);
        
        g.draw(rectangle);
        g.drawString(name, (int)rectangle.getX() + startW, (int)rectangle.getY() + startH);
        
        Debug.println(name + " drawn at " + rectangle.getLocation());
    }
    
    private Rectangle2D getOrientationMark()
    {
        int rW, rH;
        double rX = 0;
        double rY = 0;
        
        switch(rectangle.getOrientation())
        {
            case E:
                rX = rectangle.getWidth();
           
            case W:
                rX += rectangle.getX() - 3;
                rY = rectangle.getY() + rectangle.getHeight() / 2 - 1;
                
                rW = 6;
                rH = 1;
                break;
                
            case S:
                rY = rectangle.getHeight();
                
            default:
                rX = rectangle.getX() + rectangle.getWidth() / 2;
                rY += rectangle.getY() - 3;
                
                rW = 1;
                rH = 6;
        }
        
        return new Rectangle((int) rX, (int) rY, rW, rH);
    }
}
