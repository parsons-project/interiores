/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints;

import interiores.business.models.FurnitureModel;
import java.awt.Color;

/**
 * ColorConstraint represents a constraint imposed over the color of a piece of furniture
 * @author larribas
 */
public class ColorConstraint {
    
    // 'color' represents the exact color a piece of furniture should be in order to satisfy the constraint
    private Color color;
    
    /**
     * Creates a color constraint such that only those pieces of furniture matching "color" will satisfy it
     * @param color The color that will define the constraint
     */
    public ColorConstraint(Color color) {
        this.color = color;
    }
    
    /**
     * Determines whether a piece of furniture satisfies the constraint.
     * @param model The specific piece of furniture whose color is to be checked.
     * @return 'true' if the color of the model matches the constraint's. 'false' otherwise
     */
    public boolean isSatisfied(FurnitureModel model) {        
        return model.getColor().equals(color);
    }
    
    /**
     * Modifies the color defined for the constraint.
     * @param newColor The color that will override the previous one
     */
    public void changeColor(Color newColor) {
        color = newColor;
    }
    
}
