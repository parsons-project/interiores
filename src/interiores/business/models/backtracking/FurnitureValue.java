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
    private OrientedRectangle activeArea;
    
    private Rectangle passiveArea;
        
    // Represents a specific model
    private FurnitureModel model;
    
    /**
     * Creates a value from a Rectangle representing the area and orientation
     * of the furniture, and the particular model applied.
     * @param area
     * @param model 
     */
    public FurnitureValue(OrientedRectangle area, FurnitureModel model) {
        this.activeArea = area;
        this.model = model;
        this.passiveArea = computePassiveArea();        
    }
    
    
    public OrientedRectangle getArea() {
        return activeArea;
    }
    
    public Rectangle getWholeArea() {
        return passiveArea;
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

    private Rectangle computePassiveArea() {
        int o = activeArea.getOrientation().ordinal();
        int[] passiveOffsets = model.getPassiveSpace();
        return new Rectangle(activeArea.x - passiveOffsets[3-o],
                             activeArea.y - passiveOffsets[(4-o) % 4],
                             activeArea.width + passiveOffsets[(3+o) % 4] + passiveOffsets[(1+o) % 4],
                             activeArea.height + passiveOffsets[(2+o) % 4] + passiveOffsets[o]        );
    }

}
