package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
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
    
    public Rectangle getWholeArea() {
        int[] passiveOffsets = model.getPassiveSpace();
        
        Rectangle r = new Rectangle(area.x - passiveOffsets[3],
                                    area.y - passiveOffsets[0],
                                    area.width + passiveOffsets[3] + passiveOffsets[1],
                                    area.height + passiveOffsets[0] + passiveOffsets[2]);
        return r;
    }
    
    public Point getPosition() {
        return area.getLocation();
    }
    
    public FurnitureModel getModel() {
        return model;
    }
    
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");

        result.append("Model Name: " + model.getName());
        result.append(" Location: (" + area.x + "," + area.y + ")");
        result.append(" Orientation: " + area.getOrientation() + ";");

        return result.toString();
    }


    
}
