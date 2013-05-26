package interiores.business.models.room.elements.fixed;

import interiores.business.models.Orientation;
import interiores.business.models.SpaceAround;
import interiores.business.models.room.FurnitureModel;
import interiores.business.models.room.elements.WantedFixed;
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
    
    public static Point getPosition(String typeName, Orientation whichWall, int displacement,
            Dimension roomSize)
    {
        // South side of the wall fixed element points inside of the room
        Point p = new Point(-DEPTH, -DEPTH);
        
        switch(whichWall) {
            case S:
                p.y = roomSize.depth;
                            
            case N:
                p.x = displacement;
                break;
            
            case E:
                p.x = roomSize.width;
                
            case W:
            default:
                p.y = displacement;
                break;
        }
        
        if(!roomSize.contains(p, DEPTH))
            throw new BusinessException("The " + typeName + " does not fit in the room.");
        
        return p;
    }
    
    public WallFixedElement(String typeName, String color, String material, SpaceAround space,
            Orientation wall, int displacement, int length, Dimension roomSize)
    {
        super(typeName,
                getPosition(typeName, wall, displacement, roomSize),
                new FurnitureModel(typeName, new Dimension(length, DEPTH), 0.0f, color, material, space),
                wall);
    }
}
