package interiores.controllers;

import interiores.models.Room;
import interiores.mvc.Controller;

/**
 * Controller for rooms!
 * @author hector
 */
public class RoomController extends Controller
{
    private Room room;
    
    public RoomController(Room room)
    {
        this.room = room;
    }
    
    public void newRoom(String type, int width, int height)
    {
       // ...
       room.setWidth(width);
       room.setHeight(height);
    }
}
