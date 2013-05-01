package interiores.business.controllers;

import interiores.business.exceptions.NoRoomCreatedException;
import interiores.business.models.Room;
import interiores.business.models.RoomType;
import interiores.business.models.WishList;
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
        
        WishList wl = new WishList();
        data.set("wishList",wl);
        
    }
    
    public void saveRoom(String path) throws JAXBException, NoRoomCreatedException
    {
        if(! data.has("room"))
            throw new NoRoomCreatedException();
        
        data.save(data.get("room"), path);
        
        notify("roomSaved", (Model) data.get("room"));
    }
    
    public void loadRoom(String path) throws JAXBException
    {
        Room room = (Room) data.load(Room.class, path);
        data.set("room", room);
        
        notify("roomLoaded", room.toMap());
    }
}
