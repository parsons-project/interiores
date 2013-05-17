package interiores.business.events.room;

import interiores.business.models.Room;

/**
 *
 * @author hector
 */
public class RoomLoadedEvent
    extends RoomCreatedEvent
{
    public RoomLoadedEvent(Room room)
    {
        super(room);
    }
}
