/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.room.global;

import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.business.models.constraints.room.RoomPreliminarTrimmer;
import interiores.business.models.room.FurnitureModel;
import interiores.utils.CoolColor;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * All variables must have the color color.
 * @author Nil
 */
@XmlRootElement
public class SpecificColorConstraint 
    extends GlobalConstraint implements RoomPreliminarTrimmer {
    
    @XmlElement
    private CoolColor color;
    
    public SpecificColorConstraint()
    { }
    
    public SpecificColorConstraint(CoolColor color) {
        this.color = color;
    }
    
    
    @Override
    public void preliminarTrim(List<FurnitureVariable> variables, List<FurnitureConstant> fixedFurniture) {
        for (FurnitureVariable variable : variables) {
            HashSet<FurnitureModel> validModels = new HashSet(variable.getDomain().getModels(0));
            Iterator<FurnitureModel> it = validModels.iterator();
            while (it.hasNext()) {
                if (! it.next().getColor().equals(color.getColor()))
                    it.remove();
            }
            variable.eliminateExceptM(validModels);
        }
    }
    
    @Override
    public String toString() {
        return "All elements must be color " + color.name();
    }
}
