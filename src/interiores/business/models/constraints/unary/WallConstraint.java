/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.unary;

import interiores.business.models.Orientation;
import interiores.core.business.BusinessException;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hector0193
 */
public class WallConstraint
    extends AreaConstraint
{
    private Orientation[] orientations;
    
    public WallConstraint()
    { }
    
    
    public WallConstraint(int roomWidth, int roomDepth, Orientation[] orientations) {
        super();
        if (orientations.length == 0) 
            throw new BusinessException("This constraint needs at least an orientation");
        this.orientations = orientations;
        List<Point> validPositions = new ArrayList();
        
        for(Orientation orientation : orientations) {
            switch(orientation) {
                case N:
                    for (int i = 0; i < roomWidth; i++) validPositions.add(new Point(i, 0));
                    break;
                    
                case S:
                    for (int i = 0; i < roomWidth; i++) validPositions.add(new Point(i, roomDepth-1));
                    break;
                    
                case W:
                    for (int i = 0; i < roomDepth; i++) validPositions.add(new Point(0, i));
                    break;
                    
                default:
                    for (int i = 0; i < roomDepth; i++) validPositions.add(new Point(roomWidth-1, i));
                    break;
            }
            
            changePositions(validPositions);
        }
        
    }
    @Override
    public String toString() {
        String string = "";
        boolean first = true;
        for (Orientation orientation : orientations) {
            if (first) {
                string += orientation.toString();
                first = false;
            }
            else
                string += ", " + orientation;
        }
            
        return "Walls: " + string;
        
    }
}
