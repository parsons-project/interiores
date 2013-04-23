package interiores.business.models;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * This class extends java.awt.Rectangle to be used with orientations.
 * Rotations will not change the position of the upper-left corner.
 * @author alvaro
 */
public class OrientedRectangle extends Rectangle {
    private Orientation orientation;
    
    public OrientedRectangle(int x, int y, int w, int h, Orientation orientation) {
        super(x, y, w, h);
        this.orientation = orientation; 
    }
    
    public OrientedRectangle(Point point, Dimension dimension, Orientation orientation) {
        super(point, dimension);
        this.orientation = orientation; 
    }
    
    /**
     * Rotates the rectangle to the left.
     */
    public void rotateLeft() {
        setOrientation(this.orientation.rotateLeft());
    }
    
    /**
     * Rotates the rectangle to the right.
     */
    public void rotateRight() {
        setOrientation(this.orientation.rotateRight());
    }
    
    /**
     * Rotates the rectangle to an arbritrary orientation.
     * @param orientation The orientation the rectangle will be facing
     */
    public void setOrientation(Orientation orientation) {

        int tr = this.orientation.distanceRight(orientation);
        
        if (tr % 2 != 0) {
            // To rotate the rectangle without moving its position we swap
            // the height and the width only if we turned an odd number of times;
            this.setSize(this.height, this.width);
        }
        
        this.orientation = orientation;  
    }
    
    /**
     * Get the orientation of the rectangle.
     * @return The current orientation of the rectangle
     */
    public Orientation getOrientation() {
        return this.orientation;
    }
}
