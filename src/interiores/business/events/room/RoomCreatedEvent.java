package interiores.business.events.room;

import interiores.business.models.Room;
import interiores.core.Event;

/**
 *
 * @author hector
 */
public class RoomCreatedEvent
    implements Event
{
    private Room room;
    
    public RoomCreatedEvent(Room room)
    {
        this.room = room;
    }
    
    public int getWidth()
    {
        return room.getWidth();
    }
    
    public int getDepth()
    {
        return room.getDepth();
    }
    
    public String getType()
    {
        return room.getType().getName();
    }
}
