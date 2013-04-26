/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.OrientedRectangle;

/**
 * Represents a specific furniture model on a specific position and a determined orientation.
 * @author larribas
 */
public class FurnitureValue {
    
    // Represents a positioned-and-oriented area
    private OrientedRectangle area;
    
    // Represents a specific model
    private FurnitureModel model;
    
    public FurnitureValue(OrientedRectangle area, FurnitureModel model) {
        this.area = area;
        this.model = model;
    }
    
}
