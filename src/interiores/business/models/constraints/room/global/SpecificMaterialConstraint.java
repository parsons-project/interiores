/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.room.global;

import interiores.business.models.room.FurnitureModel;
import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.business.models.constraints.room.RoomPreliminarTrimmer;
import interiores.utils.Material;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * All variables must have the material material.
 * @author Nil
 */
public class SpecificMaterialConstraint 
    extends GlobalConstraint implements RoomPreliminarTrimmer {

    private Material material;

    public SpecificMaterialConstraint(Material material) {
        this.material = material;
    }
    
    
    @Override
    public void preliminarTrim(List<FurnitureVariable> variables, List<FurnitureConstant> fixedFurniture) {
        for (FurnitureVariable variable : variables) {
            HashSet<FurnitureModel> validModels = new HashSet(variable.getDomain().getModels(0));
            Iterator<FurnitureModel> it = validModels.iterator();
            while (it.hasNext()) {
                if (! it.next().getMaterial().equals(material.name()))
                    it.remove();
            }
            variable.eliminateExceptM(validModels);
        }
    }
}
