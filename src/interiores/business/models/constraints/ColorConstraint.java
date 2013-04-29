
package interiores.business.models.constraints;

import interiores.business.models.FurnitureModel;
import interiores.business.models.backtracking.FurnitureVariable;
import java.awt.Color;
import java.util.Iterator;

/**
 * ColorConstraint represents a constraint imposed over the color of a piece of furniture
 * @author larribas
 */
public class ColorConstraint
    extends UnaryConstraint {
    
    /** 
     * 'color' represents the exact color a piece of furniture should be in
     * order to satisfy the constraint.
     */
    private Color color;
    
    /**
     * Creates a color constraint such that only those pieces of furniture matching "color" will satisfy it
     * @param color The color that will define the constraint
     */
    public ColorConstraint(Color color) {
        this.color = color;
    }
    
    /**
     * Eliminates models which do not satisfy the constraint.
     * @param variable The variable whose values have to be checked.
     */
    @Override
    public void eliminateInvalidValues(FurnitureVariable variable) {
        Iterator it = variable.domainModels[0].iterator();
        while (it.hasNext()) {
            FurnitureModel model = (FurnitureModel) it.next();
            if (!model.getColor().equals(color))
                it.remove();
        }
    }
    
    /**
     * Modifies the color defined for the constraint.
     * @param newColor The color that will override the previous one
     */
    public void changeColor(Color newColor) {
        color = newColor;
    }
    
}
