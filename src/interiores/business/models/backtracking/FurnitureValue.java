package interiores.business.models.backtracking;

import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.business.models.room.FurnitureModel;
import interiores.data.adapters.OrientedRectangleAdapter;
import interiores.data.adapters.RectangleAdapter;
import interiores.shared.backtracking.Value;
import java.awt.Point;
import java.awt.Rectangle;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Represents a specific furniture model on a specific position and a determined orientation.
 * @author larribas
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FurnitureValue extends Value {
    
    // Represents a positioned-and-oriented area
    @XmlElement
    @XmlJavaTypeAdapter(OrientedRectangleAdapter.class)
    private OrientedRectangle activeArea;
    
    @XmlElement
    @XmlJavaTypeAdapter(RectangleAdapter.class)
    private Rectangle wholeArea;
        
    // Represents a specific model
    @XmlElement
    private FurnitureModel model;
    
    public FurnitureValue()
    { }
    
    /**
     * Creates a value from a Rectangle representing the area and orientation
     * of the furniture, and the particular model applied.
     * @param area
     * @param model 
     */
    public FurnitureValue(Point position, FurnitureModel model, Orientation orientation) {
        this.model = model;
        this.activeArea = model.getActiveArea(position, orientation);
        this.wholeArea = activeArea.applySpaceAround(model.getPassiveSpace());
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
