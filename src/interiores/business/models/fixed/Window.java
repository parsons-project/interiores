package interiores.business.models.fixed;

import interiores.business.models.SpaceAround;
import interiores.utils.Dimension;

/**
 * This class represents a window that the user wants to put in the room
 * @author alvaro
 */
public class Window
    extends WallFixedElement
{
    private static final int DEPTH = 5;
    
    public Window(String wall, int displacement, int length, Dimension roomSize) {
        super("window", "white", "glass", new SpaceAround(0, 0, 0, 0), wall, displacement, length, roomSize);
    }
    
}
