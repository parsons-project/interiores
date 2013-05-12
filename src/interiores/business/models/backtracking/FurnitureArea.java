/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Orientation;
import interiores.utils.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Represents a furniture model over a certain surface.
 * As such, it has an active area, a passive space at its sides,
 * a certain orientation, and represents a model.
 * @author larribas
 */
public class FurnitureArea extends Value {
    
    private Rectangle active;
    private Rectangle passive;
    private Orientation orientation;
    
    private FurnitureModel model;
    
    public FurnitureArea() {
        
    }
    
    public FurnitureArea(Point p, FurnitureModel m, Orientation o) {
        orientation = o;
        model = m;
        active = computeActiveArea(p, m.getSize(), o);
        passive = computePassiveArea(o);
    }
    
    public void changePosition(int x, int y) {
        int dx = x-active.x;
        int dy = y-active.y;
        
        active.x = x; active.y = y;
        passive.x += dx; passive.y += dy;
    }
    
    public boolean isContainedIn(Rectangle r) {
        return r.contains(getRectifiedArea("passive"));
    }
    
    public boolean collidesWith(FurnitureArea other) {
        return this.active.intersects(other.passive) || this.passive.intersects(other.active);
    }
    
    public Rectangle getRectifiedArea(String s) {
        Rectangle target = (s.equals("active"))? active : passive;
        int x = (target.width<0)? target.x + target.width : target.x;
        int y = (target.height<0)? target.y + target.height : target.height;
        return new Rectangle(x, y, Math.abs(target.width), Math.abs(target.height));
    }
    
    
    public Point getPosition() {
        return active.getLocation();
    }
    
    public FurnitureModel getModel() {
        return model;
    }
    
    
    private Rectangle computeActiveArea(Point p, Dimension d, Orientation o) {
        
        // We calculate the width parameters of the rectangle based on its orientation
        int w,h;
        if (o==o.N) { w = d.width; h = d.depth; }
        else if (o==o.E) { w = -d.depth; h = d.width; }
        else if (o==o.S) { w = -d.width; h = -d.depth; }
        else { w = d.depth; h = -d.width; }
    
        // And we assign it to a rectangle
        return new Rectangle(p.x, p.y, w, h);
    }
    
    private Rectangle computePassiveArea(Orientation orientation) {
        // We get number that corresponds to the orientation value (N=0, E=1, S=2, W=3)
        int o = orientation.ordinal();
        int[] passiveOffsets = model.getPassiveSpace();
        return new Rectangle(active.x - passiveOffsets[3-o],
                             active.y - passiveOffsets[(4-o) % 4],
                             active.width + passiveOffsets[(3+o) % 4] + passiveOffsets[(1+o) % 4],
                             active.height + passiveOffsets[(2+o) % 4] + passiveOffsets[o]        );
    }
    
    
}
