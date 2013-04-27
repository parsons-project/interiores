
package interiores.business.models;

import java.awt.Dimension;

/**
 * 
 * @author alvaro
 */
public class FurnitureType {
    
    private String name; // Name of the type
    
    private Dimension minimum; // Minimum width and "height"
    private Dimension maximum; // maximum width and "height"
    
    /**
     * Full constructor that specifies the properties of the furniture type 
     * @param name Name of the furniture type
     * @param min Minimum dimensions a model of this type should have
     * @param max Maximum dimensions a model of this type should have
     */
    public FurnitureType(String name, Dimension min, Dimension max) {
        this.name = name;
        this.minimum = min;
        this.maximum = max;
    }
    
    public String getName() {
        return name;
    }
    
    public Dimension getMinDimension() {
        return minimum;
    }
    
    public Dimension getMaxDimension() {
        return maximum;
    }
    
}
