
package interiores.business.models;

import java.awt.Dimension;

/**
 * This class represents the type of a furniture model
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
    
    /**
     * Gets the name of the type
     * @return The name of the type of furniture
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the minimum dimensions a furniture model of this type should have
     * @return The minimum dimensions a furniture model of this type should have
     */
    public Dimension getMinDimension() {
        return minimum;
    }
    
    /**
     * Gets the maximum dimensions a furniture model of this type should have
     * @return The maximum dimensions a furniture model of this type should have
     */
    public Dimension getMaxDimension() {
        return maximum;
    }
    
    
    // should this go here or maybe in the controller?
    /**
     * Checks if dimension is between the minimum and the maximum dimensions of the type
     * @param dimension The dimension to be checked
     * @return True if the dimension is between the minimum and the maximum dimensions of the type, False otherwise
     */
    public Boolean checkDimension(Dimension dimension) {
        return (minimum.width <= dimension.width && dimension.width <= maximum.width) &&
               (minimum.height <= dimension.height && dimension.height <= maximum.height);
    }
    
}
