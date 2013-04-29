
package interiores.business.models.constraints;

import interiores.business.models.FurnitureModel;
import interiores.business.models.backtracking.FurnitureVariable;
import java.util.Iterator;

/**
 * MaterialConstraint represents a constraint imposed over the material a piece of furniture is made from
 * @author larribas
 */
public class MaterialConstraint
    extends UnaryConstraint {
    
    
    /**
     * 'material' represents the exact material a piece of furniture
     * should be made from in order to satisfy the constraint
     */
    private String material;
    
    
    /**
     * Creates a material constraint such that only those pieces of furniture matching "material" will satisfy it
     * @param material The material that will define the constraint
     */
    public MaterialConstraint(String material) {
        this.material = material;
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
            if (!model.getMaterial().equals(material))
                it.remove();
        }
    }
    
    
    /**
     * Modifies material defined for the constraint
     * @param newMaterial The material that will override the previous one
     */
    public void changeMaterial(String newMaterial) {
        material = newMaterial;
    }
    
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");

        result.append(this.getClass().getName() + NEW_LINE);

        result.append("Material: " + material + NEW_LINE);
        return result.toString();
    }
    
}