package interiores.business.models.constraints.furniture.unary;

import interiores.business.models.Orientation;
import interiores.business.models.backtracking.area.Area;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.furniture.InexhaustiveTrimmer;
import interiores.business.models.constraints.furniture.UnaryConstraint;
import interiores.business.models.room.FurnitureModel;
import java.awt.Rectangle;
import java.util.List;

/**
 * The variable must be placed next to a wall identified by an orientation
 * in orientations.
 * @author nmamano
 */
public class WallConstraint
    extends UnaryConstraint implements InexhaustiveTrimmer {
    
    Orientation[] orientations;
    int roomWidth;
    int roomDepth;
    
    public WallConstraint(int roomWidth, int roomDepth, Orientation[] orientations) {
        this.orientations = orientations;
        this.roomWidth = roomWidth;
        this.roomDepth = roomDepth;
    }

    
    /**
     * Trims values that lead to no valid solution.
     * 
     * This restriction has the following particularity: as the position
     * of a variable is identified by the position of its top left corner,
     * its trivial to find the valid positions when the variable has to be
     * attached to the north or west walls. The trimP for this cases is
     * exhaustive.
     * On the other hand, for east and south walls, the distance between the
     * top left corner of a variable and the wall depend on the size of the
     * model. This is why this constraint implements the InexhaustiveTrimmer
     * interface.
     */
    @Override
    public void preliminarTrim(FurnitureVariable variable) {


        Area validPositions = new Area();
        for(Orientation orientation : orientations) {
            
            int maxDistance, minDistance;
            
            switch(orientation) {
                case N:
                    validPositions.union(
                        new Area(new Rectangle(0,0,roomWidth,1)));
                    break;

                case W:
                    validPositions.union(
                        new Area(new Rectangle(0,0,1,roomDepth)));
                    break;
                                     
                //in this case, we have to leave positions such that any
                //model of the variable can be next to the wall
                case S:
                    minDistance = 0; //least depth of a model of this variable
                    maxDistance = 0; //largest depth of a model of this variable
                    
                    //initialize max and min distance
                    for (FurnitureModel model : variable.getDomain().getModels(0)) {
                        int depth = model.getSize().depth;
                        if (minDistance == 0 || depth < minDistance)
                            minDistance = depth;
                        if (maxDistance == 0 || depth > maxDistance)
                            maxDistance = depth;
                    }
                    
                    validPositions.union(new Area(new Rectangle(
                        0, roomDepth - maxDistance,
                        roomWidth, maxDistance - minDistance)));
                        //x goes from 0 to roomWidth to reach the whole south
                        //wall.
                        //y goes from roomDepth - maxDistance to roomDepth -
                        //minDistance so that any model with depth between
                        //minDistance and maxDistance can fit
                    break;
                                     
                case E:
                    minDistance = 0; //least width of a model of this variable
                    maxDistance = 0; //largest width of a model of this variable
                    
                    //initialize max and min distance
                    for (FurnitureModel model : variable.getDomain().getModels(0)) {
                        int width = model.getSize().width;
                        if (minDistance == 0 || width < minDistance)
                            minDistance = width;
                        if (maxDistance == 0 || width > maxDistance)
                            maxDistance = width;
                    }
                    
                    validPositions.union(new Area(new Rectangle(
                        roomWidth - maxDistance, 0,
                        maxDistance - minDistance, roomDepth)));
                        //y goes from 0 to roomDepth to reach the whole east
                        //wall.
                        //x goes from roomWidth - maxDistance to roomWidth -
                        //minDistance so that any model with width between
                        //minDistance and maxDistance can fit
                    break;
            }    
        }
        
        variable.eliminateExceptP(validPositions);
    }

    
    @Override
    public boolean isSatisfied(FurnitureVariable variable) {
        for(Orientation orientation : orientations) {
            FurnitureValue assignedValue = variable.getAssignedValue();
            switch(orientation) {
                case E:
                    if (assignedValue.getPosition().x +
                        assignedValue.getModel().getSize().width
                            != roomWidth)
                        return false;
                    break;

                case S:
                    if (assignedValue.getPosition().y +
                        assignedValue.getModel().getSize().depth
                            != roomDepth)
                        return false;                    
                    break;
                    
                default: //the trims on N or W walls are exhaustive
                    break;
            }
        }
        return true;
    }
}
