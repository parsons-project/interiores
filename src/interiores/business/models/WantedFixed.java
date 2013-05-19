package interiores.business.models;

import interiores.core.business.BusinessException;
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
        super(typeName, typeName);
        model = new FurnitureModel(name, size, 0, color, material, space);
    }
    
    public void setName(String name) {
        this.name = name;
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
}
