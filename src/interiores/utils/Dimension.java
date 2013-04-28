package interiores.utils;

/**
 *
 * @author hector
 */
public class Dimension extends java.awt.Dimension
{
    public Dimension() {
        this(0, 0);
    }
    
    public Dimension(int width, int height) {
        super(width, height);
    }
    
    @Override
    public String toString() {
        return "(" + width + ", " + height + ")";
    }
}
