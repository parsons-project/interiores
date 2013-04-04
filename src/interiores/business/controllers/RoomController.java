package interiores.business.controllers;

import interiores.business.models.Room;
import interiores.core.business.BusinessController;
import interiores.data.DataController;

/**
 * Controller for rooms!
 * @author hector
 */
public class RoomController extends BusinessController
{
    public RoomController(DataController data)
    {
        super(data);
    }
    
    public void newRoom(String type, int width, int height)
    {
       Room room = new Room(type, width, height);
       
       data.set("room", room);
       notify("room.created", room.toMap());
    }
}
