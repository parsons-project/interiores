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
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * All variables must have the color color.
 * @author Nil
 */
public class SpecificColorConstraint 
    extends GlobalConstraint implements RoomPreliminarTrimmer {

    private Color color;

    public SpecificColorConstraint(Color color) {
        this.color = color;
    }
    
    
    @Override
    public void preliminarTrim(List<FurnitureVariable> variables, List<FurnitureConstant> fixedFurniture) {
        for (FurnitureVariable variable : variables) {
            HashSet<FurnitureModel> validModels = new HashSet(variable.getDomain().getModels(0));
            Iterator<FurnitureModel> it = validModels.iterator();
            while (it.hasNext()) {
                if (! it.next().getColor().equals(color))
                    it.remove();
            }
            variable.eliminateExceptM(validModels);
        }
    }
}
