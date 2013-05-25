package interiores.business.models.constraints.room.global;

import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.furniture.PreliminarTrimmer;
import java.awt.Rectangle;

/**
 *
 * @author hector
 */
public class SpaceRespectingConstraint
    implements PreliminarTrimmer
{
    @Override
    public void preliminarTrim(FurnitureConstant[] constants, FurnitureVariable[] variables) {
        for(FurnitureConstant constant : constants)
            trimArea(constant, variables);
    }
    
    private void trimArea(FurnitureConstant constant, FurnitureVariable[] variables) {
        Rectangle area = constant.getAssignedValue().getWholeArea();
        
        for(FurnitureVariable variable : variables)
            variable.getDomain().trimInvalidRectangle(area, 0);
    }
}
