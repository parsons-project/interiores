package interiores.business.models.constraints.furniture.unary;

import interiores.business.models.FurnitureModel;
import interiores.business.models.backtracking.Domain;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.furniture.UnaryConstraint;
import interiores.core.business.BusinessException;
import interiores.data.adapters.ColorAdapter;
import interiores.shared.backtracking.Variable;
import interiores.utils.CoolColor;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
    @XmlJavaTypeAdapter(ColorAdapter.class)
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
    public void preliminarTrim(FurnitureVariable variable) {
        HashSet<FurnitureModel> validModels = variable.getDomain().getModels(0);
        Iterator<FurnitureModel> it = validModels.iterator();
        while (it.hasNext()) {
            if (! it.next().getColor().equals(color))
                it.remove();
        }
        
        variable.eliminateExceptM(validModels);
    }
    
    
    @Override
    public String toString() {
        return this.getClass().getName() + " Color: " + color;
    }
}
