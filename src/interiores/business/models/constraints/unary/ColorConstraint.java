package interiores.business.models.constraints.unary;

import interiores.business.models.backtracking.Domain;
import interiores.business.models.constraints.UnaryConstraint;
import interiores.business.models.room.FurnitureModel;
import interiores.core.business.BusinessException;
import interiores.utils.CoolColor;
import java.util.Iterator;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ColorConstraint represents a constraint imposed over the color of a piece of furniture
 * @author larribas
 */
@XmlRootElement
public class ColorConstraint
    extends UnaryConstraint {
    
    /** 
     * 'color' represents the exact color a piece of furniture should be, in
     * order to satisfy the constraint.
     */
    @XmlAttribute
    private CoolColor color;
    
    public ColorConstraint() {
        
    }
        
    /**
     * Creates a color constraint such that only those pieces of furniture matching "color" will satisfy it
     * @param color The color that will define the constraint
     */
    public ColorConstraint(CoolColor color) {
        this.color = color;
    }
    
    public ColorConstraint(String color)
            throws BusinessException
    {
        this(CoolColor.getEnum(color));
    }
    
    /**
     * Eliminates models which do not satisfy the constraint.
     * @param variable The variable whose values have to be checked.
     */
    @Override
    public void eliminateInvalidValues(Domain domain) {
        Iterator it = domain.getModels(0).iterator();
        while (it.hasNext()) {
            FurnitureModel model = (FurnitureModel) it.next();
            if (!model.getColor().equals(color.getColor()))
                it.remove();
        }
    }
    
    
    @Override
    public String toString() {
        return this.getClass().getName() + " Color: " + color;
    }
}
