package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.shared.backtracking.Value;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Represents a specific furniture model on a specific position and a determined orientation.
 * @author larribas
 */
public class FurnitureValue extends Value {
    
    // Represents a positioned-and-oriented area
    private OrientedRectangle activeArea;
    
    private Rectangle wholeArea;
        
    // Represents a specific model
    private FurnitureModel model;
    
    /**
     * Creates a value from a Rectangle representing the area and orientation
     * of the furniture, and the particular model applied.
     * @param area
     * @param model 
     */
    public FurnitureValue(Point position, FurnitureModel model, Orientation orientation) {
        this.model = model;
        this.activeArea = model.getActiveArea(position, orientation);
        this.wholeArea = activeArea.getWholeArea(model.getPassiveSpace());
    }
    
    
    public OrientedRectangle getArea() {
        return activeArea;
    }
    
    public Rectangle getWholeArea() {
        return wholeArea;
    }
    
    public Point getPosition() {
        return activeArea.getLocation();
    }
    
    public FurnitureModel getModel() {
        return model;
    }
    
    @Override
    public String toString() {
        return "Model Name: " + model.getName() + " Location: (" + activeArea.x + "," + activeArea.y + ")" +
                " Orientation: " + activeArea.getOrientation() + ";";
    }
}
