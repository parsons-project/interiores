package interiores.business.models.room.elements;

import interiores.business.models.Orientation;
import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.room.FurnitureModel;
import java.awt.Point;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents a given FixedElement wanted by the user
 * @author alvaro
 */
@XmlRootElement
public class WantedFixed
    extends FurnitureConstant
{
    public WantedFixed()
    {}
    
    public WantedFixed(String typeName, Point position, FurnitureModel model, Orientation orientation) {
        super(typeName, new FurnitureValue(position, model, orientation));
    }
}
