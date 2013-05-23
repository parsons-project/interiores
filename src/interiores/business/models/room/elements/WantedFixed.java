package interiores.business.models.room.elements;

import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.business.models.SpaceAround;
import interiores.business.models.room.FurnitureModel;
import interiores.utils.Dimension;
import java.awt.Point;

/**
 * This class represents a given FixedElement wanted by the user
 * @author alvaro
 */
public class WantedFixed extends WantedElement
{
    protected Point position;
    protected Orientation orientation;
    protected FurnitureModel model;

    public WantedFixed(String typeName, Dimension size, String color, String material, SpaceAround space) {
        super(typeName);
        model = new FurnitureModel(name, size, 0, color, material, space);
    }
    
    public Point getPosition() {
        return position;
    }
    
    public void setPosition(Point position) {
        this.position = position;
    }
    
    public Orientation getOrientation() {
        return orientation;
    }
    
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
    
    /**
     * For compatibility with a furnitureType wich has furnitureModels
     * @return A list with one FurnitureModel with the attributes of this wantedFixed element
     * @throws BusinessException 
     */
    public FurnitureModel getModel() {
        return model;
    }
    
    public OrientedRectangle getActiveArea() {
        return model.getActiveArea(position, orientation);
    }
}
