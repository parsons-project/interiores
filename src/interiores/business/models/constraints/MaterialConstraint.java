/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints;

import interiores.business.models.FurnitureModel;

/**
 * MaterialConstraint represents a constraint imposed over the material a piece of furniture is made from
 * @author larribas
 */
public class MaterialConstraint extends ModelConstraint {
    
    // 'material' represents the exact material a piece of furniture
    // should be made from in order to satisfy the constraint
    private String material;
    
    /**
     * Creates a material constraint such that only those pieces of furniture matching "material" will satisfy it
     * @param material The material that will define the constraint
     */
    public MaterialConstraint(String material) {
        this.material = material;
    }
    
    /**
     * Determines whether a piece of furniture satisfies the constraint
     * @param model The specific piece of furniture whose material is to be checked.
     * @return 'true' if the material of the model matches the constraint's
     */
    public boolean isSatisfied(FurnitureModel model) {        
        return model.getMaterial().equals(material);
    }
    
    /**
     * Modifies material defined for the constraint
     * @param newMaterial The material that will override the previous one
     */
    public void changeMaterial(String newMaterial) {
        material = newMaterial;
    }
    
}
