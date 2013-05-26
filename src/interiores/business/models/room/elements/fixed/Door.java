package interiores.business.models.room.elements.fixed;

import interiores.business.models.SpaceAround;
import interiores.utils.Dimension;

/**
 * This class represents a door that the user wants to put in the room
 * @author alvaro
 */
public class Door
    extends WallFixedElement
{
    public static String getColor() {
        return "brown";
    }
    
    public Door(String wall, int displacement, int length, Dimension roomSize)
    {
        super("door", getColor(), "wood", new SpaceAround(0, 0, length, 0), wall, displacement, length,
                roomSize);
    }  
}
