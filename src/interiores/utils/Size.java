/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.utils;

/**
 *
 * @author larribas
 */
public class Size {
    
    int width, depth;
    
    public Size() {
        
    }
    
    public Size(int width, int depth) {
        this.width = width;
        this.depth = depth;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getDepth() {
        return depth;
    }
    
    public boolean isLargerThan(Size s) {
        return width >= s.width && depth >= s.depth;
    }
    
    public boolean isSmallerThan(Size s) {
        return width <= s.width && depth <= s.depth;
    }
    
    
    
    
}
