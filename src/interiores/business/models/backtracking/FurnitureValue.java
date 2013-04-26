/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.OrientedRectangle;
import interiores.shared.Value;
import java.awt.Point;

/**
 * Represents a specific furniture model on a specific position and a determined orientation.
 * @author larribas
 */
public class FurnitureValue extends Value {
    
    // Represents a positioned-and-oriented area
    private OrientedRectangle area;
    
    // Represents a specific model
    private FurnitureModel model;
    
    /**
     * Creates a value from a Rectangle representing the area and orientation
     * of the furniture, and the particular model applied.
     * @param area
     * @param model 
     */
    public FurnitureValue(OrientedRectangle area, FurnitureModel model) {
        this.area = area;
        this.model = model;
    }
    
    
    public OrientedRectangle getArea() {
        return area;
    }
    
    public Point getPosition() {
        return area.getLocation();
    }
    
    public FurnitureModel getModel() {
        return model;
    }
    
    
    
}
