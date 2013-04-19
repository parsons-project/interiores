/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.presentation.swing.views.map;

import java.awt.Color;
import java.awt.Graphics;
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
    
    public Furniture(String name, int x, int y, int width, int height)
    {
        this.name = name;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        
        color = Color.cyan;
    }
    
    public void setColor(String c)
    {
        color = Color.decode(c);
    }
    
    @Override
    public void draw(Graphics g)
    {
        g.setColor(color);
        g.fillRect(x, y, width, height);
        
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);
        
        Rectangle2D r = g.getFontMetrics().getStringBounds(name, g);
        
        int stringW = (int) r.getWidth();
        int stringH = (int) r.getHeight();
        int startW = width/2 - stringW/2;
        int startH = height/2 + stringH/2;
            
        g.drawString(name, x + startW, y + startH);
    }
}
