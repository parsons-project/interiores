package interiores.business.models;

import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureValue;
import java.awt.Point;

/**
 * This class represents a given FixedElement wanted by the user
 * @author alvaro
 */
public class WantedFixed
    extends FurnitureConstant
{
    public WantedFixed(String typeName, Point position, FurnitureModel model, Orientation orientation) {
        super(typeName, typeName, new FurnitureValue(position, model, orientation));
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
