package interiores.business.models;

import interiores.business.models.constraints.unary.PositionConstraint;
import interiores.core.business.BusinessException;
import interiores.utils.Dimension;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a given FixedElement wanted by the user
 * @author alvaro
 */
public class WantedFixed extends WantedElement {
    
    Dimension size;
    SpaceAround space;
    String color;
    String material;

    public WantedFixed(String name, String typeName, Point position, Dimension size,
                String color, String material, SpaceAround space) {
        super(name, typeName);
        super.addUnaryConstraint(new PositionConstraint(position));
        this.size = size;
        this.color = color;
        this.material = material;
        this.space = space;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * For compatibility with a furnitureType wich has furnitureModels
     * @return A list with one FurnitureModel with the attributes of this wantedFixed element
     * @throws BusinessException 
     */
    public List<FurnitureModel> getModels() throws BusinessException {
        return Arrays.asList(new FurnitureModel(name, size, 0, color, material, space));
    }
    
}
