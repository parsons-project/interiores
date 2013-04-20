package interiores.presentation.swing.views.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author hector0193
 */
public class Furniture implements Drawable
{
    private String name;
    private int width;
    private int height;
    private int x;
    private int y;
    private Color color;
    private char orientation;
    
    public Furniture(String name, int x, int y, int width, int height)
    {
        this.name = name;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        
        color = Color.cyan;
        orientation = 'D';
    }
    
    public void setColor(String c)
    {
        color = Color.decode(c);
    }
    
    public void setOrientation(char o)
    {
        orientation = o;
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        Rectangle2D f = getRectangle();
        g.setColor(color);
        g.fill(f);
        
        g.setColor(Color.black);
        g.draw(f);
        
        Rectangle2D l = getOrientationMark();
        g.setColor(Color.black);
        g.fill(l);
        
        Rectangle2D r = g.getFontMetrics().getStringBounds(name, g);
        
        int stringW = (int) r.getWidth();
        int stringH = (int) r.getHeight();
        int startW = ((int)f.getWidth())/2 - stringW/2;
        int startH = ((int)f.getHeight())/2 + stringH/2;
        
        g.setColor(Color.black);
        g.drawString(name, (int)f.getX() + startW, (int)f.getY() + startH);
        
    }
    
    private Rectangle2D getRectangle()
    {
        Rectangle2D r;
        
        switch(orientation)
        {
            case 'L':
            case 'R':
                r = new Rectangle(x + GridMap.getPadding(), y + GridMap.getPadding(), height, width);
                break;
                
            case 'U':
            default:
                r = new Rectangle(x + GridMap.getPadding(), y + GridMap.getPadding(), width, height);
        }
        
        return r;
    }
    
    private Rectangle2D getOrientationMark()
    {
        int rW, rH;
        int rX = 0;
        int rY = 0;
        
        switch(orientation)
        {
            case 'R':
                rX = height;
           
            case 'L':
                rX += x + GridMap.getPadding() - 3;
                rY = y + GridMap.getPadding() + width/2 - 1;
                
                rW = 6;
                rH = 1;
                break;
                
            case 'D':
                rY = height;
                
            default:
                rX = x + GridMap.getPadding() + width/2;
                rY += y + GridMap.getPadding() - 3;
                
                rW = 1;
                rH = 6;
        }
        
        return new Rectangle(rX, rY, rW, rH);
    }
}
