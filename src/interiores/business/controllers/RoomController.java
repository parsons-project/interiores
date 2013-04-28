package interiores.business.controllers;

import interiores.business.models.Room;
import interiores.business.models.RoomType;
import interiores.core.business.BusinessController;
import interiores.core.business.Model;
import interiores.core.data.JAXBDataController;
import javax.xml.bind.JAXBException;

/**
 * Controller for rooms!
 * @author hector
 */
public class RoomController extends BusinessController
{
    public RoomController(JAXBDataController data)
    {
        super(data);
    }
    
    public void newRoom(String typeName, int width, int height)
    {
        RoomType type = new RoomType(typeName);
        Room room = new Room(type, width, height);

        data.set("room", room);
        notify("roomCreated", room);
    }
    
    public void saveRoom(String path)
    {
        try
        {
            data.save(data.get("room"), path);
            notify("roomSaved", (Model) data.get("room"));
        }
        catch(JAXBException e)
        {
            e.printStackTrace();
        }
    }
    
    public void loadRoom(String path)
    {
        try
        {
            Room room = (Room) data.load(Room.class, path);
            data.set("room", room);
            
            notify("roomLoaded", room.toMap());
        }
        catch(JAXBException e)
        {
            e.printStackTrace();
        }
    }
}
