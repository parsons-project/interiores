/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Orientation;
import interiores.shared.backtracking.Value;
import interiores.utils.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Represents a furniture model over a certain surface.
 * As such, it has an active area, a passive space at its sides,
 * a certain orientation, and represents a model.
 * @author larribas
 */
public class FurnitureValueAlt extends Value {
    
    private Rectangle active;
    private Rectangle passive;
    private Orientation orientation;
    
    private FurnitureModel model;
        
    /**
     * Builds a furniture area whose model is m, extending from a point p,
     * and with an orientation o
     * @param p The point from which the active area of the furniture extends
     * @param m The model of the furniture value
     * @param o The orientation of the furniture value
     */
    public FurnitureValueAlt(Point p, FurnitureModel m, Orientation o) {
        orientation = o;
        model = m;
        active = computeActiveArea(p, m.getSize(), o);
        passive = computePassiveArea();
    }
    
    /**
     * Changes the position the furniture is placed at
     * @param x The new x coordinate
     * @param y  The new y coordinate
     */
    public void changePosition(int x, int y) {
        int dx = x-active.x;
        int dy = y-active.y;
        
        active.x = x; active.y = y;
        passive.x += dx; passive.y += dy;
    }
    
    /**
     * Finds out whether this furniture area is contained within a given rectangle
     * @param r The container rectangle
     * @return 'true' if FArea is contained within r. 'false' otherwise
     */
    public boolean isContainedIn(Rectangle r) {
        return r.contains(getRectifiedArea(false));
    }
    
    /**
     * Finds out whether this furniture area is contained within a given rectangle
     * @param other The other FArea
     * @return 'true' if the two rectangles collide. That is, if the active area
     * of the former intersects with the passive area of the latter, or vice versa.
     */
    public boolean collidesWith(FurnitureValueAlt other) {
        return this.active.intersects(other.passive) || this.passive.intersects(other.active);
    }
    
    /**
     * Obtains a rectified rectangle representing the active or passive area if the current one.
     * A rectified rectangle meant to represent a canonical shape (with positive measures),
     * devoid of orientation, so that it can be manipulated in contexts that require this kind of treatment
     * @param act A boolean representing whether we want the active (true) or passive (false) rectified rectangle
     * @return A rectified rectangle created from the active or passive area, as specified
     */
    public Rectangle getRectifiedArea(boolean act) {
        Rectangle target = (act)? active : passive;
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
    
    /**
     * Computes the appropriate active area, given a reference point, a dimension and an orientation.
     * @param p A Point of reference
     * @param d The Dimension of the furniture
     * @param o An Orientation
     * @return A Rectangle representing the active area of such a configuration
     */
    private Rectangle computeActiveArea(Point p, Dimension d, Orientation o) {
        
        // We calculate the width parameters of the rectangle based on its orientation
        int w,h;
        if (o==Orientation.N)       { w =  d.width; h =  d.depth; }
        else if (o==Orientation.E)  { w = -d.depth; h =  d.width; }
        else if (o==Orientation.S)  { w = -d.width; h = -d.depth; }
        else                        { w =  d.depth; h = -d.width; }
    
        // And we assign it to a rectangle
        return new Rectangle(p.x, p.y, w, h);
    }
    
    /**
     * Computes the passive area of the current configuration.
     * PRE: 'model', 'active', and 'orientation' have already been given a value
     * @return A Rectangle representing the passive area of such a configuration
     */
    private Rectangle computePassiveArea() {
        // We get number that corresponds to the orientation value (N=0, E=1, S=2, W=3)
        int o = orientation.ordinal();
        int[] passiveOffsets = model.getPassiveSpace();
        return new Rectangle(active.x - passiveOffsets[3-o],
                             active.y - passiveOffsets[(4-o) % 4],
                             active.width + passiveOffsets[(3+o) % 4] + passiveOffsets[(1+o) % 4],
                             active.height + passiveOffsets[(2+o) % 4] + passiveOffsets[o]        );
    }
    
    
}
