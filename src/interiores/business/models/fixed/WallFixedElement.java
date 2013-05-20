package interiores.business.models.fixed;

import interiores.business.models.Orientation;
import interiores.business.models.SpaceAround;
import interiores.business.models.WantedFixed;
import interiores.core.business.BusinessException;
import interiores.utils.Dimension;
import java.awt.Point;

/**
 *
 * @author hector
 */
public class WallFixedElement
    extends WantedFixed
{
    private static final int DEPTH = 5;
    
    public WallFixedElement(String typeName, String color, String material, SpaceAround space,
            String wall, int displacement, int length, Dimension roomSize)
    {
        super(typeName, new Dimension(length, DEPTH), color, material, space);
        
        // South side of the wall fixed element points inside of the room
        Point position = new Point(-DEPTH, -DEPTH);

        Orientation whichWall = Orientation.getEnum(wall);
        
        switch(whichWall) {
            case S:
                position.y = roomSize.depth;
                            
            case N:
                position.x = displacement;
                break;
            
            case E:
                position.x = roomSize.width;
                
            case W:
            default:
                position.y = displacement;
                break;
        }
        
        if(!roomSize.contains(position, DEPTH))
            throw new BusinessException("The " + typeName + " does not fit in the room.");
        
        setPosition(position);
        setOrientation(whichWall.complementary());
    }
}
