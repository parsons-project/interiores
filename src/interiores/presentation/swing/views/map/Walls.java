package interiores.presentation.swing.views.map;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author hector
 */
public class Walls implements Drawable {
    private static final int DEPTH = 5;
    private static final String COLOR = "0x999999";
    private int width;
    private int height;
    
    public Walls(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.decode(COLOR));
        
        int padding = GridMap.getPadding();
        
        g.fillRect(padding - DEPTH, padding - DEPTH, width + DEPTH * 2, DEPTH);
        g.fillRect(padding - DEPTH, padding + height, width + DEPTH * 2, DEPTH);
        g.fillRect(padding - DEPTH, padding, DEPTH, height);
        g.fillRect(padding + width, padding, DEPTH, height);
    }
}
