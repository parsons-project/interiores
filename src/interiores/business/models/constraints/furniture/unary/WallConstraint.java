/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.furniture.unary;

import interiores.business.models.Orientation;
import interiores.business.models.backtracking.Area.Area;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hector0193
 */
public class WallConstraint
    extends AreaConstraint {
    
    public WallConstraint(int roomWidth, int roomDepth, Orientation[] orientations) {
        super();
        
        Area validPositions;
        
        for(Orientation orientation : orientations) {
            switch(orientation) {
                case N:
                    validPositions = new Area(new Rectangle(0,0,roomWidth,1));
                    break;
                
                    //not implemented! depends on the models!
                case S:
                    validPositions = new Area(new Rectangle(0,0,roomWidth,roomDepth));
                    break;
                    
                case W:
                    validPositions = new Area(new Rectangle(0,0,1,roomDepth));
                    break;
                    //not implemented! depends on the models!                   
                default:
                    validPositions = new Area(new Rectangle(0,0,roomWidth,roomDepth));
                    break;
            }
            
            changePositions(validPositions);
        }
    }
}
